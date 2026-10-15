package com.todolistbackend.dto;


public class UserResponseDto {
	
	private Long userId;
	private String UserName;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}

	@Override
	public String toString() {		
		return String.format("{UserID: %d,\nUserName: %s}", getUserId(), getUserName());
	}
}
