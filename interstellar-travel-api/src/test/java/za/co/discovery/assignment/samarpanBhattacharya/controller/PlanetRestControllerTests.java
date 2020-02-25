package za.co.discovery.assignment.samarpanBhattacharya.controller;

import za.co.discovery.assignment.samarpanBhattacharya.controller.PlanetRestController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.service.PlanetService;

@WebMvcTest(PlanetRestController.class)
public class PlanetRestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlanetService planetService;

	@Test
	public void testApis() throws Exception {
		Planet p = new Planet();
		p.setId(23);
		p.setName("Earth");
		p.setNode("E1");

		List<Planet> planets = Arrays.asList(p);
		given(planetService.getAllPlanets()).willReturn(planets);

		this.mockMvc
			.perform(get("/api/current/planets")
				.with(user("user@demo.com")
					.password("password")
					.roles("USER", "ADMIN")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].name", is(p.getName())));
	}
}
