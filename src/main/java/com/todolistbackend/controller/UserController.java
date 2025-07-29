package com.todolistbackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.service.TodoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UserController {
	
	
	final TodoService todoService;
	
	@Autowired
	public UserController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	static Logger logger = Logger.getLogger("User Controller");

	@PostMapping("/createList")
	public ResponseEntity<TodoList> createList(@Valid @RequestBody TodoList list){
		Optional<TodoList> newList = todoService.saveList(list);
		if (newList.isEmpty()) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok().body(newList.get());
	}
	
	@GetMapping("/getSingleList")
	public TodoList getSingleList(@RequestParam(name = "lead") Long id) {
		Optional<TodoList> list =  todoService.getSingleList(id);
		if (list.isPresent()) return list.get();
		return new TodoList();
	}
	
	@GetMapping("/getAllList")
	public List<TodoList> getAllList() {
		List<TodoList> allList =  todoService.getAllList();
		return allList;
	}
	
	@GetMapping("/deleteList")
	public ResponseEntity<String> deleteList(@RequestParam(name = "lead") Long id) {
		if (todoService.deleteList(id))	 return ResponseEntity.ok().body("List has been deleted.");
		return ResponseEntity.badRequest().body("List does not exists.");

	}
		
	@GetMapping("/deleteAllList")
	public boolean deleteAllList() {
		return todoService.deleteAllList();
	}
	
	@PatchMapping("/updateList")
	public ResponseEntity<TodoList> updateList(@RequestBody TodoList body, @RequestParam(name = "lead") Long id) {
		if (body.getId() == null) body.setId(id);
		Optional<TodoList> newList = todoService.updateList(body);
		return newList.map(todo -> ResponseEntity.status(HttpStatus.ACCEPTED).body(todo)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PutMapping("replaceList")
	public ResponseEntity<TodoList> replaceList(@Valid @RequestBody TodoList body, @RequestParam("lead") Long id){
		Optional<TodoList> replacedList = todoService.replaceList(body, id);
		if (replacedList.isEmpty()) return ResponseEntity.status(403).build();
		return ResponseEntity.status(203).body(replacedList.get());
	}
	
//	@GetMapping("/test")
//	public String getAllParams(@RequestParam Map<String, String> allParms) {
//		StringBuffer Parms = new StringBuffer();
//		for(Map.Entry<String, String> parm: allParms.entrySet()) {
//			Parms.append(String.format("%s: %s\n", parm.getKey(), parm.getValue()));
//		}
//		return Parms.toString();
//	}
	
	@GetMapping("/csrftoken")
	public CsrfToken getCSRFtoken(HttpServletRequest req) {
		return (CsrfToken) req.getAttribute("_csrf");
		
	}
}
