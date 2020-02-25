package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import za.co.discovery.assignment.samarpanBhattacharya.dao.RouteDao;
import za.co.discovery.assignment.samarpanBhattacharya.dao.RouteDaoImpl;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteDao routeDao;

	@Autowired
	private RouteDaoImpl routeDaoImpl;

	@Override
	public List<Route> getAllRoutes() {
		List<Route> routeList = null;
		try {
			routeList = (List<Route>) routeDao.findAll();
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return routeList;
	}

	@Override
	public List<Route> addRoutes(List<Route> routeList) {
		List<Route> savedRouteList;
		try {
			savedRouteList = (List<Route>) routeDao.saveAll(routeList);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return savedRouteList;
	}

	@Override
	public void deleteRoute(int id) {
		try {
			routeDao.deleteById(id);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}

	}

	@Override
	public Route updateRoute(Route route) {
		Route updatedRoute;
		try {
			updatedRoute = routeDao.save(route);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return updatedRoute;
	}

	@Override
	public List<Route> getRoutesByPlanet(Planet planet) {
		List<Route> routeList;
		try {
			routeList = routeDaoImpl.getRoutesByPlanet(planet);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return routeList;
	}
}
