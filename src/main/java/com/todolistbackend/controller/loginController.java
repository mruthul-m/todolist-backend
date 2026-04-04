package com.todolistbackend.controller;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolistbackend.dto.UserLoginDto;
import com.todolistbackend.dto.UserRequestDto;
import com.todolistbackend.dto.UserResponseDto;
import com.todolistbackend.service.JwtService;
import com.todolistbackend.service.LoginService;

import jakarta.validation.Valid;

@RestController
public class loginController {
	
	static final Logger loginLogs = Logger.getLogger("LOGIN");
	
	final LoginService loginService;
	final JwtService jwtService;
	final AuthenticationManager authManager;
	
	public loginController(LoginService loginService, JwtService jwtService, AuthenticationManager authManager) {
		this.loginService = loginService;
		this.jwtService = jwtService;
		this.authManager = authManager;
	}
	

	@GetMapping("/")
	public String loginPage() {
		return "<h1>this is working</h1><h3>So this</h3>";
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<UserResponseDto> getUser(@RequestParam("id") Long id) {
		UserResponseDto resUser = loginService.getSingleUser(id);
		if (!(resUser == null))
			return new ResponseEntity<UserResponseDto>(resUser, HttpStatus.CREATED);
		return new ResponseEntity<UserResponseDto>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto requestDto) {
		UserResponseDto responseDTO = loginService.saveUser(requestDto);
		if (!(responseDTO == null))
		return new ResponseEntity<UserResponseDto>(responseDTO,HttpStatus.CREATED);
		return new ResponseEntity<UserResponseDto>(HttpStatus.CONFLICT);
	}
	
	
	@PostMapping("/auth")
	public String userAuth(@RequestBody UserLoginDto authReq) throws UserPrincipalNotFoundException {
		
		Authentication authentication = 
		authManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
		
		if (authentication.isAuthenticated()) {
			loginLogs.info("User authenticated");
			String jwtToken = jwtService.generateToken(authReq.getUserName());
			loginLogs.info("JWT Token: "+ jwtToken);
			return jwtToken;
		}else {
			loginLogs.log(Level.SEVERE, "Username not found");
			throw new UserPrincipalNotFoundException("User Not exists");
		}
		

	}
	
}
