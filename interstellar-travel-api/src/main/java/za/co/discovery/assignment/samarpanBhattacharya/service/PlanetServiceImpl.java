package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDao;
import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDaoImpl;
import za.co.discovery.assignment.samarpanBhattacharya.dao.RouteDao;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Service
public class PlanetServiceImpl implements PlanetService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlanetDao planetDao;

	@Autowired
	private PlanetDaoImpl planetDaoImpl;

	@Autowired
	private RouteService routeService;

	@Autowired
	private RouteDao routeDao;

	@SuppressWarnings("unused")
	@Override
	public List<Planet> getAllPlanets() {
		List<Planet> planetList;
		try {
			planetList = (List<Planet>) planetDao.findAll();
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return planetList;
	}

	@Override
	public List<Planet> addPlanets(List<Planet> planetList) {
		List<Planet> savedPlanetList = null;
		try {
			savedPlanetList = (List<Planet>) planetDao.saveAll(planetList);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return savedPlanetList;
	}

	@Override
	public Planet getPlanetByNodeName(String node) {
		Planet planet = planetDaoImpl.getPlanetByNodeName(node);
		return planet;
	}

	@Override
	public void deletePlanet(int id) {
		try {
			Planet planet = planetDao.findById(id).get();
			List<Route> routeList = routeService.getRoutesByPlanet(planet);
			for (Route route : routeList) {
				routeDao.delete(route);
			}
			planetDao.deleteById(id);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public Planet updatePlanet(Planet planet) {
		Planet updatedPlanet;
		try {
			updatedPlanet = planetDao.save(planet);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return updatedPlanet;
	}
}
