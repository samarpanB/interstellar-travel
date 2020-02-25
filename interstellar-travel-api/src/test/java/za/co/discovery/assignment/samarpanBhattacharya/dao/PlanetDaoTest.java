/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.assignment.samarpanBhattacharya.dao;

import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDaoImpl;
import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDao;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;

@SpringBootTest
public class PlanetDaoTest {

	private static final String PLANET_NAME = "Planet Bong";
	private static final String PLANET_NODE = "PB1";

	@Autowired
	PlanetDao planetDao;

	@Autowired
	PlanetDaoImpl planetDaoImpl;

	@Test
	public void testPersistence() {
		// given
		Planet p = new Planet();
		p.setName(PLANET_NAME);
		p.setNode(PLANET_NODE);

		// when
		planetDao.save(p);

		// then
		Planet p2 = planetDaoImpl.getPlanetByNodeName(PLANET_NODE);
		Assert.assertNotNull(p2);
		Assert.assertEquals(PLANET_NAME, p2.getName());
		Assert.assertEquals(PLANET_NODE, p2.getNode());

		planetDao.deleteAll();
	}

	@Test
	public void testUpdate() {
		Planet p = new Planet();
		p.setName(PLANET_NAME);
		p.setNode(PLANET_NODE);

		planetDao.save(p);

		Planet planet = planetDaoImpl.getPlanetByNodeName(PLANET_NODE);
		String planetName = "NewPlanet";
		planet.setName(planetName);

		planetDao.save(planet);

		Planet p2 = planetDaoImpl.getPlanetByNodeName(PLANET_NODE);
		Assert.assertEquals(planetName, p2.getName());

		planetDao.deleteAll();
	}

	@Test
	public void testFetch() {
		Planet p = new Planet();
		p.setName(PLANET_NAME);
		p.setNode(PLANET_NODE);

		planetDao.save(p);
		List<Planet> planetList = (List<Planet>) planetDao.findAll();

		Assert.assertNotNull(planetList);
		Assert.assertEquals(PLANET_NAME, planetList.get(0).getName());
		Assert.assertEquals(PLANET_NODE, planetList.get(0).getNode());
		planetDao.deleteAll();
	}

	@Test
	public void testDelete() {
		Planet p = new Planet();
		p.setName(PLANET_NAME);
		p.setNode(PLANET_NODE);
		planetDao.save(p);

		Planet p1 = planetDaoImpl.getPlanetByNodeName(PLANET_NODE);
		Assert.assertNotNull(p1);

		planetDao.delete(p1);

		Planet p2 = planetDaoImpl.getPlanetByNodeName(PLANET_NODE);
		Assert.assertNull(p2);
	}
}
