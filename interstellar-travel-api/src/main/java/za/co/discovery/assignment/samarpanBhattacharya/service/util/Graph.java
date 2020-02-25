package za.co.discovery.assignment.samarpanBhattacharya.service.util;

import java.util.List;

public class Graph {

	private final List<Vertex> vertices;
	private final List<Edge> edges;

	public Graph(List<Vertex> vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public List<Vertex> getVertexes() {
		return vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}
}
