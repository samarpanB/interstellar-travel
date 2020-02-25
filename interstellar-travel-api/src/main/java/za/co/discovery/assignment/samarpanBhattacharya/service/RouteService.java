package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.util.List;

import org.springframework.stereotype.Service;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Service
public interface RouteService {

	public List<Route> getAllRoutes();

	public List<Route> addRoutes(List<Route> routeList);

	public void deleteRoute(int id);

	public Route updateRoute(Route route);

	public List<Route> getRoutesByPlanet(Planet planet);
}
