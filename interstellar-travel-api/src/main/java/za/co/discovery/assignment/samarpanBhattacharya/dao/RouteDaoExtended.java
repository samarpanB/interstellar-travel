package za.co.discovery.assignment.samarpanBhattacharya.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Repository
interface RouteDaoExtended {

	/**
	 * To fetch planets by node name
	 * @param node
	 * @return
	 */
	public List<Route> getRoutesByPlanet(Planet planet);
}
