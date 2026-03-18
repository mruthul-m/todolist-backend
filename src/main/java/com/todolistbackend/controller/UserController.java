package com.todolistbackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	static Logger logger = Logger.getLogger("User Controller");

	@PostMapping("/createList")
	public ResponseEntity<TodosDto> createList(@RequestBody TodoList list, @PathVariable("userId") Long userId){
		list.setFk_user_id(userId);
		Optional<TodosDto> newList = todoService.saveList(list);
		if (newList.isEmpty()) return ResponseEntity.badRequest().build();
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
	
	@GetMapping("/deleteList/{id}")
	public ResponseEntity<String> deleteList(@PathVariable("id") Long id) {
		if (todoService.deleteList(id))	 return ResponseEntity.ok().body("List has been deleted.");
		return ResponseEntity.badRequest().body("List does not exists.");

	}
		
	@GetMapping("/deleteAllList")
	public boolean deleteAllList() {
		return todoService.deleteAllList();
	}
	
	@PatchMapping("/updateList/{id}")
	public ResponseEntity<TodoList> updateList(@RequestBody TodoList body, @PathVariable("id") Long id) {
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
	
	@GetMapping("/public")
	public String greeting() {
		return "greeting";
	}
}
