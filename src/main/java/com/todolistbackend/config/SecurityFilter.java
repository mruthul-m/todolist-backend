package com.todolistbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//Security configurations

@Configuration
@EnableWebSecurity
public class SecurityFilter {
	
	
//	final UserDetailsService userDetailsService;
//	
//
//	public SecurityFilter(UserDetailsService serivce) {
//		this.userDetailsService = serivce;
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(cutomizer -> cutomizer.disable())
			.headers(headers -> headers
					.frameOptions(frame -> frame.disable()))
			.authorizeHttpRequests(req -> req.
					requestMatchers("/h2-console/**")
					.permitAll().
					anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults()) //enables postman like application basic authentication
			.formLogin(Customizer.withDefaults()) // enables forms in the web page
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions are able to create (every time new session generates)
			.build();

	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user1 = User.builder().username("admin").password("{noop}admin@123").roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user1);
	}
}
