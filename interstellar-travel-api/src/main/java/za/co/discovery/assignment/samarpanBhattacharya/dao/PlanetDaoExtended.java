package za.co.discovery.assignment.samarpanBhattacharya.dao;

import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;

@Repository
interface PlanetDaoExtended {

	/**
	 * To fetch planets by node name
	 * @param node
	 * @return
	 */
	Planet getPlanetByNodeName(String node);
}
