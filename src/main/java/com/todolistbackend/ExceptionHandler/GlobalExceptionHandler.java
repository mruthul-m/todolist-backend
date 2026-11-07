package com.todolistbackend.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todolistbackend.customexceptions.UserExistException;
import com.todolistbackend.dto.ApiResponse;
import com.todolistbackend.dto.UserResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<ApiResponse<UserResponseDto>> handleUserExistException(UserExistException ex){
		ApiResponse<UserResponseDto> response = new ApiResponse<UserResponseDto>(false, ex.getMessage(), ex.getUserResponseDto());
		return ResponseEntity.status(409).body(response);
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleAllExceptions(Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Something went wrong: " + ex.getMessage());
	    }
}
