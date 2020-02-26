package za.co.discovery.assignment.samarpanBhattacharya.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@SpringBootTest
public class RouteDaoTest {

	@Autowired
	private RouteDao routeDao;

	@Autowired
	private PlanetDao planetDao;

	private static final String PLANET_NAME = "Planet Bong";
	private static final String PLANET_NODE = "P1";
	private static final String PLANET_NAME2 = "Alpha Centauri";
	private static final String PLANET_NODE2 = "A1";

	@Test
	public void testPersistence() {
		Route route = saveRoute();
		List<Route> routeList = routeDao.getRoutesByPlanet(route.getSource());

		Assert.assertNotNull(routeList);
		Assert.assertEquals(PLANET_NAME, routeList.get(0).getSource().getName());

		routeDao.deleteAll();
		planetDao.deleteAll();
	}

	@Test
	public void testUpdate() {
		Route route = saveRoute();
		Float distance = 13.13f;
		route.setDistance(distance);
		Route r2 = routeDao.save(route);

		Assert.assertNotNull(r2);
		Assert.assertEquals(distance, r2.getDistance());
		routeDao.deleteAll();
		planetDao.deleteAll();
	}

	@Test
	public void testFetch() {
		saveRoute();
		List<Route> routetList = (List<Route>) routeDao.findAll();

		Assert.assertNotNull(routetList);
		Assert.assertEquals(PLANET_NAME, routetList.get(0).getSource().getName());
		routeDao.deleteAll();
		planetDao.deleteAll();
	}

	@Test
	public void testDelete() {
		Route route = saveRoute();

		routeDao.delete(route);

		List<Route> routeList = routeDao.getRoutesByPlanet(route.getSource());
		Assert.assertNull(routeList);
		planetDao.deleteAll();
	}

	private Route saveRoute() {
		Planet p1 = new Planet();
		p1.setName(PLANET_NAME);
		p1.setNode(PLANET_NODE);

		Planet p2 = new Planet();
		p2.setName(PLANET_NAME2);
		p2.setNode(PLANET_NODE2);
		Route route = new Route();
		route.setSource(p1);
		route.setDestination(p2);
		route.setDistance(12.12f);
		planetDao.save(p1);
		planetDao.save(p2);
		return routeDao.save(route);
	}
}
