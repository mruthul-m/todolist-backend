package com.todolistbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.repo.LoginRepository;
import com.todolistbackend.repo.TodoRepository;

@Service
public class TodoService {

	@Autowired
	TodoRepository todoRepository;
	
	@Autowired
	LoginRepository loginRepository;

	public Optional<TodoList> saveList(TodoList list) {	
		TodoList newList;
		try {
			newList = todoRepository.save(list);
			return Optional.of(newList);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().split("\\(")[0].trim());
		}
		
		return Optional.empty();
	}
	
	public Optional<TodoList> getSingleList(Long id) {
			Optional<TodoList> list = todoRepository.findById(id);
			return list;
	}
	
	public List<TodoList> getAllList() {
		List<TodoList> fullList = (List<TodoList>) todoRepository.findAll();
		return fullList;
	}
	
	public boolean deleteList(Long id) {
		boolean isExist = todoRepository.existsById(id);
		if (isExist) {
			todoRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public boolean deleteAllList() {
		todoRepository.deleteAll();
		return true;
	}
	
	public Optional<TodoList> updateList(TodoList list) {
		Optional<TodoList> oldList = todoRepository.findById(list.getId());
		if (oldList.isEmpty()) return oldList;
		if (list.getTaskHeading() == null) list.setTaskHeading(oldList.get().getTaskHeading());
		if (list.getTaskContent() == null) list.setTaskContent(oldList.get().getTaskContent());
		if (list.getFk_user_id() == null) list.setFk_user_id(oldList.get().getFk_user_id());
		if (list.getCreatedAt() == null) list.setCreatedAt(oldList.get().getCreatedAt());
		list.setUpdatedAt(LocalDateTime.now());
		todoRepository.save(list);
		return todoRepository.findById(list.getId());
		
	}
	
	public Optional<TodoList> replaceList(TodoList list, Long id) {
		if (! deleteList(id)) return Optional.empty();
		TodoList newList =  todoRepository.save(list);
		return Optional.of(newList);
	}

}
