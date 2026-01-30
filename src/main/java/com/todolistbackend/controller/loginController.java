package com.todolistbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolistbackend.dto.UserRequestDto;
import com.todolistbackend.dto.UserResponseDto;
import com.todolistbackend.service.LoginService;

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
	
}
