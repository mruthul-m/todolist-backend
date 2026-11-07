package com.todolistbackend.customexceptions;

import com.todolistbackend.dto.UserResponseDto;

public class UserExistException extends Exception{
	private static final long serialVersionUID = 1L;   	
	private UserResponseDto userResponseDto;
	public UserExistException(String message, UserResponseDto dto) {
		super(message);
		this.setUserResponseDto(dto);
	}
	public UserResponseDto getUserResponseDto() {
		return userResponseDto;
	}
	public void setUserResponseDto(UserResponseDto userResponseDto) {
		this.userResponseDto = userResponseDto;
	}

}
