package com.todolistbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//Security configurations

@Configuration
@EnableWebSecurity
public class SecurityFilter {
	
	
	final UserDetailsService userDetailsService;
	

	public SecurityFilter(UserDetailsService serivce) {
		this.userDetailsService = serivce;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(cutomizer -> cutomizer.disable())
			.authorizeHttpRequests(req -> req.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults()) //enables postman like application basic authentication
//			.formLogin(Customizer.withDefaults()) // enables forms in the web page
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions are able to create (every time new session generates)
			.build();

	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
////		default user authentication 'not recommended and not secure'
//		UserDetails userDetails = User.withDefaultPasswordEncoder()
//			.username("kiran")
//			.password("k@123")
//			.roles("USER")
//			.build();
//		
//		UserDetails userDetails2 = User.withDefaultPasswordEncoder()
//				.username("abhiraj")
//				.password("abhi!!")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(userDetails, userDetails2);
//	}	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}
}
