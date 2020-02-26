package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDao;
import za.co.discovery.assignment.samarpanBhattacharya.dao.RouteDao;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Edge;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Graph;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.ShortestPathAlgorithm;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Vertex;

@Service("interstellarTravelService")
class InterstellarTravelServiceImpl implements InterstellarTravelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ListeningExecutorService service;

	@Autowired
	private RouteDao routeDao;

	@Autowired
	private PlanetDao planetDao;


	public InterstellarTravelServiceImpl() {
		service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
	}

	@Override
	public List<Route> calculateShortestPath(final String source,
		final String destination) {
		List<Route> result = new ArrayList<Route>();

		try {
			result = calculatePath(source, destination);
		} catch (Exception e) {
			logger.info("calculateShortestPath failed " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		}

		return result;
	}

	@Override
	public ListenableFuture<List<Route>> calculateShortestPathAsync(final String source,
		final String destination) {
		ListenableFuture<List<Route>> result;
		result = (ListenableFuture<List<Route>>) service.submit(() -> {
			return calculatePath(source, destination);
		});

		return result;
	}

	private List<Route> calculatePath(String source, String destination) {
		try {
			List<Planet> planetList = (List<Planet>) planetDao.findAll();
			List<Vertex> vertices = this.getVerticesFromPlanets(planetList);

			List<Route> routeList = (List<Route>) routeDao.findAll();
			List<Edge> edges = this.getEdgesFromRoutes(routeList);

			Planet sourcePlanet = planetList.stream()
				.filter(planet -> planet.getName().equals(source))
				.findFirst().get();
			Vertex sourceVertex = vertices.stream()
				.filter(vertex -> vertex.getId().equals(sourcePlanet.getId().toString()))
				.findFirst().get();

			Planet destinationPlanet = planetList.stream()
				.filter(planet -> planet.getName().equals(destination))
				.findFirst().get();
			Vertex destVertex = vertices.stream()
				.filter(vertex -> vertex.getId().equals(destinationPlanet.getId().toString()))
				.findFirst().get();

			Graph graph = new Graph(vertices, edges);
			ShortestPathAlgorithm dijkstra = new ShortestPathAlgorithm(graph);
			LinkedList<Vertex> path = dijkstra.execute(sourceVertex, destVertex);

			// Now convert vertex back to planet
			LinkedList<Planet> planetPath = new LinkedList<>();
			path.forEach(vertex -> {
				Planet foundPlanet = planetList.stream()
					.filter(planet -> planet.getId().toString().equals(vertex.getId()))
					.findFirst().get();
				planetPath.add(foundPlanet);
			});

			return this.getHops(planetPath, routeList);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private List<Route> getHops(LinkedList<Planet> path, List<Route> routeList) {
		LinkedList<Route> hops = new LinkedList<>();
		ListIterator<Planet> pathIterator = path.listIterator(0);
		while (pathIterator.hasNext()) {
			Planet source = pathIterator.next();
			Planet dest = pathIterator.next();

			Route routeFound = routeList.stream()
				.filter(route -> route.getSource().getId().equals(source.getId())
				&& route.getDestination().getId().equals(dest.getId()))
				.findFirst().get();

			hops.add(routeFound);
			// Go back once if we haven't reached the end yet
			if (pathIterator.hasNext()) {
				pathIterator.previous();
			}
		}

		return new ArrayList<>(hops);
	}

	private List<Vertex> getVerticesFromPlanets(List<Planet> planetList) {
		return planetList.stream()
			.map(planet -> this.convertToVertex(planet))
			.collect(Collectors.toList());
	}

	private List<Edge> getEdgesFromRoutes(List<Route> routeList) {
		return routeList.stream()
			.map(route -> {
				Vertex source = this.convertToVertex(route.getSource());
				Vertex dest = this.convertToVertex(route.getDestination());
				return new Edge(route.getId().toString(), source, dest, route.getDistance());
			}).collect(Collectors.toList());
	}

	private Vertex convertToVertex(Planet p) {
		return new Vertex(p.getId().toString(), p.getName());
	}
}
