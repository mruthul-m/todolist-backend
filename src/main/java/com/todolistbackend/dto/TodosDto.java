package com.todolistbackend.dto;

import java.time.LocalDateTime;

public class TodosDto {
	
	private Long id;
	private String taskHeading;
	private String taskContent;
	private LocalDateTime createdAt;
	
	public TodosDto(String taskHeading, String taskContent, LocalDateTime createdAt) {
		this.taskHeading = taskHeading;
		this.taskContent = taskContent;
		this.createdAt = createdAt;
	}
	
	public TodosDto(Long id, String taskHeading, String taskContent, LocalDateTime createdAt) {
		this.id = id;
		this.taskHeading = taskHeading;
		this.taskContent = taskContent;
		this.createdAt = createdAt;
	}
	public String getTaskHeading() {
		return taskHeading;
	}
	public void setTaskHeading(String taskHeading) {
		this.taskHeading = taskHeading;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
