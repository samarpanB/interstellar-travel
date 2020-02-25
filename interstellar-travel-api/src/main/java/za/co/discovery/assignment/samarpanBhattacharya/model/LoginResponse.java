package za.co.discovery.assignment.samarpanBhattacharya.model;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {

	private String message;
	private String token;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
