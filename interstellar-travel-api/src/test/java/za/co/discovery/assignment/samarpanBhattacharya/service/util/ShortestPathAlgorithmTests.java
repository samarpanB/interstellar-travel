package za.co.discovery.assignment.samarpanBhattacharya.service.util;

import za.co.discovery.assignment.samarpanBhattacharya.service.util.ShortestPathAlgorithmImpl;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Vertex;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Graph;
import za.co.discovery.assignment.samarpanBhattacharya.service.util.Edge;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortestPathAlgorithmTests {

	private List<Vertex> nodes;
	private List<Edge> edges;

	@Test
	public void testExecute() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			Vertex location = new Vertex("Node_" + i, "Node_" + i);
			nodes.add(location);
		}

		addLane("Edge_0", 0, 1, 85);
		addLane("Edge_1", 0, 2, 217);
		addLane("Edge_2", 0, 4, 173);
		addLane("Edge_3", 2, 6, 186);
		addLane("Edge_4", 2, 7, 103);
		addLane("Edge_5", 3, 7, 183);
		addLane("Edge_6", 5, 8, 250);
		addLane("Edge_7", 8, 9, 84);
		addLane("Edge_8", 7, 9, 167);
		addLane("Edge_9", 4, 9, 502);
		addLane("Edge_10", 9, 10, 40);
		addLane("Edge_11", 1, 10, 600);

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(nodes, edges);
		ShortestPathAlgorithmImpl dijkstra = new ShortestPathAlgorithmImpl(graph);
		LinkedList<Vertex> path = dijkstra.execute(nodes.get(0), nodes.get(10));

		Assert.assertNotNull(path);
		Assert.assertTrue(path.size() > 0);

		path.forEach((vertex) -> {
			System.out.println(vertex);
		});

	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo,
		int duration) {
		Edge lane = new Edge(laneId,
			nodes.get(sourceLocNo),
			nodes.get(destLocNo),
			duration);
		edges.add(lane);
	}
}
