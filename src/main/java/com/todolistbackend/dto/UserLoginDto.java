package com.todolistbackend.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLoginDto implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String name;
	private String password;

	public UserLoginDto() { 
	}

	public UserLoginDto(String name, String password) {
		this.setName(name);
		this.password = password;
	}

	

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return getName();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+getUsername();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
