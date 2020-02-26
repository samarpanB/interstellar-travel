package za.co.discovery.assignment.samarpanBhattacharya.dao;

import org.springframework.stereotype.Service;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;

@Service
public interface PlanetDaoExtended {

	/**
	 * To fetch planets by node name
	 * 
	 * @param node
	 * @return
	 */
	public Planet getPlanetByNodeName(String node);
}
