package za.co.discovery.assignment.samarpanBhattacharya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;
import za.co.discovery.assignment.samarpanBhattacharya.service.RouteService;

@RequestMapping("/api/{[v1|current]}")
@RestController
public class RouteRestController {

	@Autowired
	private RouteService routeService;

	@PostMapping("/routes")
	public ResponseEntity<List<Route>> addRoutes(@RequestBody List<Route> routeList) {
		List<Route> savedRouteList = (List<Route>) routeService.addRoutes(routeList);
		return new ResponseEntity<>(savedRouteList, HttpStatus.OK);
	}

	@GetMapping("/routes")
	public ResponseEntity<List<Route>> getAllRoutes() {
		List<Route> routeList = routeService.getAllRoutes();
		return new ResponseEntity<>(routeList, HttpStatus.OK);
	}

	@DeleteMapping("/routes/{id}")
	public ResponseEntity<JSONObject> deleteRoute(@PathVariable int id) {
		JSONObject response = new JSONObject();
		routeService.deleteRoute(id);
		response.put("response", "Deleted");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/routes")
	public ResponseEntity<?> updateRoute(@RequestBody Route route) {
		if (route.getId() != null) {
			Route updatedRoute = routeService.updateRoute(route);
			return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
		} else {
			JSONObject response = new JSONObject();
			response.put("response", "Route Id not exist in the request body");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
