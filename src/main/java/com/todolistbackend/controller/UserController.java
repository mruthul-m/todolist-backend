package com.todolistbackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.dto.TodosDto;
import com.todolistbackend.service.TodoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/{userId}")
@RestController
public class UserController {
	
	
	final TodoService todoService;
	
	
	public UserController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	static Logger logger = Logger.getLogger("Todos");

	@PostMapping("/createList")
	public ResponseEntity<TodosDto> createList(@RequestBody TodoList list, @PathVariable("userId") Long userId){
		logger.info("Attempting to create new TodoList");
		list.setFk_user_id(userId);
		System.out.println(list); // to be deleted
		Optional<TodosDto> newList = todoService.saveList(list);
		if (newList.isEmpty()) {
			logger.warning("Issue while creating new Todo");
			return ResponseEntity.badRequest().build();
		}
		logger.info("New Todo is Created");
		return ResponseEntity.ok().body(newList.get());
	}
	
	@GetMapping("/getSingleList/{id}")
	public  ResponseEntity<TodosDto> getTodo( @PathVariable("id") Long id) {
		Optional<TodosDto> list =   todoService.getSingleList(id);
		if (list.isPresent()) {
			return ResponseEntity.ok().body(list.get());
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/getAllList")
	public List<TodosDto> getAllList(@PathVariable("userId") Long userId) {
		List<TodosDto> allList =  todoService.getAllList(userId);
		return allList;
	}
	
	@DeleteMapping("/deleteList/{id}")
	public ResponseEntity<String> deleteList(@PathVariable("id") Long id) {
		if (todoService.deleteList(id))	 return ResponseEntity.ok().body("List has been deleted.");
		return ResponseEntity.badRequest().body("List does not exists.");

	}
		
	@DeleteMapping("/deleteAllList")
	public boolean deleteAllList(@PathVariable("userId") Long userId) {
		return todoService.deleteAllList(userId);
	}
	
	@PatchMapping("/updateList/{id}")
	public ResponseEntity<TodosDto> updateList(@RequestBody TodoList body, @PathVariable("id") Long id,
			@PathVariable("userId") Long userId) {
		body.setFk_user_id(userId);
		if (body.getId() == null) body.setId(id);
		Optional<TodosDto> newList = todoService.updateList(body);
		return newList.map(todo -> ResponseEntity.status(HttpStatus.ACCEPTED).body(todo)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PutMapping("replaceList/{id}")
	public ResponseEntity<TodosDto> replaceList(@Valid @RequestBody TodoList body,
										@PathVariable("id") Long id, @PathVariable("userId") Long userId){
		body.setFk_user_id(userId);
		body.setId(id);
		Optional<TodosDto> replacedList = todoService.replaceList(body);		
		return replacedList.map(todo -> ResponseEntity.status(203).body(replacedList.get())).orElse(ResponseEntity.status(403).build());
	}
	
	
	@GetMapping("/csrftoken")
	public CsrfToken getCSRFtoken(HttpServletRequest req) {
		return (CsrfToken) req.getAttribute("_csrf");
		
	}
	
}
