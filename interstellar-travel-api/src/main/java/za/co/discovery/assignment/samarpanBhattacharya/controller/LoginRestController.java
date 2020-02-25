package za.co.discovery.assignment.samarpanBhattacharya.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.co.discovery.assignment.samarpanBhattacharya.model.LoginRequest;
import za.co.discovery.assignment.samarpanBhattacharya.model.LoginResponse;

@RequestMapping("/{[v1|current]}")
@RestController
public class LoginRestController {

	@Value("${email}")
	private String email;

	@Value("${password}")
	private String password;

	@Autowired
	private LoginResponse loginResponse;

	@PostMapping("/auth/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		if (email.equals(loginRequest.getEmail()) && password.equals(loginRequest.getPassword())) {
			String stringToEncode = loginRequest.getEmail()
				+ ':' + loginRequest.getPassword();
			String encodedString
				= Base64.getEncoder().withoutPadding().encodeToString(stringToEncode.getBytes());
			loginResponse.setMessage("success");
			loginResponse.setToken(encodedString);
			return new ResponseEntity<>(loginResponse, HttpStatus.OK);
		} else {
			loginResponse.setMessage("failure");
			return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
		}
	}
}
