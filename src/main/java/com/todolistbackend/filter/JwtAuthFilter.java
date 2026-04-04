package com.todolistbackend.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todolistbackend.service.JwtService;
import com.todolistbackend.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	final JwtService jwtService;
	final LoginService loginService;
	public JwtAuthFilter(JwtService jwtService, LoginService loginService) {
		this.jwtService = jwtService;
		this.loginService = loginService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		String authHeader = request.getHeader("Authorization");

		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // every bearer token starts as 'bearer <token>', so just trims the first 7
												// characters.
			username = jwtService.extractUsername(token);

			// 2. Validate token and get user details (Logic varies by provider)
			if (username != null &&  SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails =  loginService.loadUserByUsername(username);
				if(jwtService.validateToke(username, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
					
				
				
			}

		}
		
		filterChain.doFilter(request, response);
	}
}
