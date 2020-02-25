package za.co.discovery.assignment.samarpanBhattacharya.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;

@Repository
public interface PlanetDao extends CrudRepository<Planet, Integer> {

}
