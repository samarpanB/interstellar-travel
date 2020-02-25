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
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.service.PlanetService;

@RequestMapping("/api/{[v1|current]}")
@RestController
public class PlanetRestController {

	@Autowired
	private PlanetService planetService;

	@PostMapping("/planets")
	public ResponseEntity<List<Planet>> addPlanets(@RequestBody List<Planet> planetList) {
		List<Planet> savedPlanetList = (List<Planet>) planetService.addPlanets(planetList);
		return new ResponseEntity<>(savedPlanetList, HttpStatus.OK);
	}

	@GetMapping("/planets")
	public ResponseEntity<List<Planet>> getAllPlanets() {
		List<Planet> planetList = planetService.getAllPlanets();
		return new ResponseEntity<List<Planet>>(planetList, HttpStatus.OK);
	}

	@DeleteMapping("/planets/{id}")
	public ResponseEntity<JSONObject> deletePlanet(@PathVariable int id) {
		JSONObject response = new JSONObject();
		planetService.deletePlanet(id);
		response.put("response", "Deleted");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/planets")
	public ResponseEntity<?> updatePlanet(@RequestBody Planet planet) {
		if (planet.getId() != null) {
			Planet updatedPlanet = planetService.updatePlanet(planet);
			return new ResponseEntity<>(updatedPlanet, HttpStatus.OK);
		} else {
			JSONObject response = new JSONObject();
			response.put("response", "Planet Id not exist in the request body");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
