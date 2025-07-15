package com.todolistbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolistbackend.Models.User;
import com.todolistbackend.services.LoginService;

import jakarta.validation.Valid;

@RestController
public class loginController {
	
	@Autowired
	LoginService loginService;

	@GetMapping("/")
	public String loginPage() {
		return "<h1>this is working</h1><h3>So this</h3>";
	}
	
	@GetMapping("/getUser")
	public User getUser(@RequestParam("id") Long id) {
		User reqUser = loginService.getSingleUser(id);
		return reqUser;
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
		if (loginService.saveUser(user))
		return new ResponseEntity<String>("New user has been created.",HttpStatus.CREATED);
		return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);
	}
	
}
