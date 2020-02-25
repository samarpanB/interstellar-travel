/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.assignment.samarpanBhattacharya.soap;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;
import za.co.discovery.assignment.samarpanBhattacharya.service.InterstellarTravelService;

@Endpoint
public class RouteDetailsEndpoint {

	@Autowired
	private InterstellarTravelService interstellarTravelService;

	@PayloadRoot(namespace = "https://www.discovery.co.za/ws/routes", localPart = "GetRouteDetailsRequest")
	@ResponsePayload
	public GetRouteDetailsResponse processShortestPathRequest(@RequestPayload GetRouteDetailsRequest request) {
		GetRouteDetailsResponse response = new GetRouteDetailsResponse();
		List<RouteDetails> routeDetailsList = new ArrayList<>();
		List<Route> routes = interstellarTravelService.calculateShortestPath(request.getSource(), request.getDestination());

		routes.stream().map((Route r) -> {
			RouteDetails rd = new RouteDetails();
			rd.setId(r.getId());
			rd.setSource(r.getSource().getName());
			rd.setDestination(r.getDestination().getName());
			rd.setDistance(r.getDistance());
			rd.setTraffic(r.getTraffic());
			return rd;
		}).forEachOrdered((rd) -> {
			routeDetailsList.add(rd);
		});

		response.setRouteDetailsList(routeDetailsList);

		return response;
	}
}
