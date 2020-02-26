package za.co.discovery.assignment.samarpanBhattacharya.dao;

import org.springframework.data.repository.CrudRepository;

import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

public interface RouteDao extends CrudRepository<Route, Integer>, RouteDaoExtended {

}
