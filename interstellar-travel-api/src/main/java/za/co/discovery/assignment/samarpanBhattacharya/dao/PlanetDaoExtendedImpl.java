package za.co.discovery.assignment.samarpanBhattacharya.dao;

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

@Repository
@Transactional
public class PlanetDaoExtendedImpl implements PlanetDaoExtended {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Planet getPlanetByNodeName(String node) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Planet> cq = cb.createQuery(Planet.class);

		Root<Planet> root = cq.from(Planet.class);
		cq.select(root).where(cb.like(root.get("node"), node));
		Planet planetEntity;
		try {
			planetEntity = session.createQuery(cq).uniqueResult();
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}
		return planetEntity;
	}
}
