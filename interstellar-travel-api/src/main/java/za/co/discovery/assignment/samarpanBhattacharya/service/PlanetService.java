package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.util.List;

import org.springframework.stereotype.Service;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;

@Service
public interface PlanetService {

	public List<Planet> getAllPlanets();

	public List<Planet> addPlanets(List<Planet> planetList);

	public Planet getPlanetByNodeName(String node);

	public void deletePlanet(int id);

	public Planet updatePlanet(Planet planet);
}
