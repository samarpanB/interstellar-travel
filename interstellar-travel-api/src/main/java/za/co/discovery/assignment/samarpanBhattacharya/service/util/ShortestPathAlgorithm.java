package za.co.discovery.assignment.samarpanBhattacharya.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortestPathAlgorithm {

	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Float> distance;

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		adjacentNodes.stream().filter((target)
			-> (getShortestDistance(target) > getShortestDistance(node)
			+ getDistance(node, target))).map((target) -> {
			distance.put(target, getShortestDistance(node)
				+ getDistance(node, target));
			return target;
		}).map((target) -> {
			predecessors.put(target, node);
			return target;
		}).forEachOrdered((target) -> {
			unSettledNodes.add(target);
		});

	}

	private float getDistance(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
				&& edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<>();
		edges.stream().filter((edge) -> (edge.getSource().equals(node)
			&& !isSettled(edge.getDestination())))
			.forEachOrdered((edge) -> {
				neighbors.add(edge.getDestination());
			});
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	private float getShortestDistance(Vertex destination) {
		Float d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	public ShortestPathAlgorithm(Graph graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<>(graph.getVertexes());
		this.edges = new ArrayList<>(graph.getEdges());
	}

	public void executeOnSource(Vertex source) {
		settledNodes = new HashSet<>();
		unSettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, Float.MIN_VALUE);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/*
	* This method returns the path from the source to the selected target and
	* NULL if no path exists
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<>();
		Vertex step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}

	public LinkedList<Vertex> execute(Vertex source, Vertex target) {
		this.executeOnSource(source);
		return this.getPath(target);
	}
}
