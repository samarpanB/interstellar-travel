package za.co.discovery.assignment.samarpanBhattacharya.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${email}")
	private String email;

	@Value("${password}")
	private String password;

	@Value("${allowed-domains}")
	private String allowedDomains;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
			//HTTP Basic authentication
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/api/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, "/api/**").hasRole("USER")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("USER")
			.antMatchers("/ws/**").permitAll()
			.and()
			.csrf().disable()
			.formLogin().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth.inMemoryAuthentication()
			.withUser(email).password(encoder.encode(password)).roles("USER");
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(allowedDomains));
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList(
			HttpMethod.GET.toString(),
			HttpMethod.PUT.toString(),
			HttpMethod.POST.toString(),
			HttpMethod.DELETE.toString(),
			HttpMethod.OPTIONS.toString()));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
