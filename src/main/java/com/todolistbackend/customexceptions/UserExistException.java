package com.todolistbackend.customexceptions;

import com.todolistbackend.dto.UserResponseDto;

public class UserExistException extends Exception{
	private static final long serialVersionUID = 1L;   	
	private final UserResponseDto userResponseDto = new UserResponseDto();
	public UserExistException(String message, String username) {
		super(message);
		this.userResponseDto.setUserName(username);
	}
	public UserResponseDto getUserResponseDto() {
		return userResponseDto;
	}
	
}
