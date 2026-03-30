package com.todolistbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.dto.TodosDto;
import com.todolistbackend.repo.TodoRepository;

@Service
public class TodoService {

	final TodoRepository todoRepository;
	
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	

	public Optional<TodosDto> saveList(TodoList list) {	
		TodoList newList;
		try {
			newList = todoRepository.save(list);
			return Optional.of(new TodosDto(
					newList.getId(),
					newList.getTaskHeading(),
					newList.getTaskContent(), 
					newList.getCreatedAt()));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().split("\\(")[0].trim());
		}
		
		return Optional.empty();
	}
	
	public Optional<TodosDto> getSingleList(Long id) {
			Optional<TodoList> todo = todoRepository.findById(id);
			if (todo.isPresent()) {
				return Optional.of(new 
						TodosDto(todo.get().getId(),
								todo.get().getTaskHeading(),
								todo.get().getTaskContent(),
								todo.get().getCreatedAt()));
			}else
				return Optional.empty();
			
	}
	
	public List<TodosDto> getAllList(Long userId) {
		List<TodosDto> fullList = (List<TodosDto>) todoRepository.findByUserId(userId);		
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
	
	public boolean deleteAllList(Long userId) {
		return 0 < todoRepository.deleteAllByUserId(userId);
		
	}
	
	public Optional<TodosDto> updateList(TodoList list) {
		Optional<TodoList> oldList = todoRepository.findById(list.getId());
		if (oldList.isEmpty()) return Optional.empty();
		if (list.getTaskHeading() == null) list.setTaskHeading(oldList.get().getTaskHeading());
		if (list.getTaskContent() == null) list.setTaskContent(oldList.get().getTaskContent());
		list.setCreatedAt(oldList.get().getCreatedAt());
		list.setUpdatedAt(LocalDateTime.now());
		list = todoRepository.save(list);
		System.out.println(list);
		return Optional.of(new TodosDto(list.getId(), list.getTaskHeading(), list.getTaskContent(), list.getCreatedAt()));
		
	}
	
	public Optional<TodosDto> replaceList(TodoList list) {
		LocalDateTime newTime = LocalDateTime.now();
		list.setCreatedAt(newTime);
		list.setUpdatedAt(newTime);
		list = todoRepository.save(list);
		return Optional.of(new TodosDto(list.getId(), list.getTaskHeading(), list.getTaskContent(), list.getCreatedAt()));
		
	}

}
