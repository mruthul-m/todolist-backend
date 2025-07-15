package com.todolistbackend.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "todo")
public class TodoList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "heading")
	@NotBlank
	private String taskHeading;
	
	@Column(name = "content")
	@NotBlank
	private String taskContent;
	
	
	private boolean status;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	private Long fk_user_id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getFk_user_id() {
		return fk_user_id;
	}
	public void setFk_user_id(Long fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	@Override
	public String toString() {
		return "TodoList [id=" + id + ", taskHeading=" + taskHeading + ", taskContent=" + taskContent + ", status="
				+ status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", fk_user_id=" + fk_user_id + "]";
	}
	
	


}
