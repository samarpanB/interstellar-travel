package za.co.discovery.assignment.samarpanBhattacharya.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Repository
@Transactional
public class RouteDaoImpl {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Route> getRoutesByPlanet(Planet planet) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Route> cq = cb.createQuery(Route.class);

		Root<Route> root = cq.from(Route.class);
		cq.select(root).where(cb.or((cb.equal(root.get("source"), planet.getId())), (cb.equal(root.get("destination"), planet.getId()))));
		List<Route> routeList = null;
		try {
			routeList = session.createQuery(cq).getResultList();
			if (routeList.size() == 0) {
				routeList = null;
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return routeList;
	}
}
