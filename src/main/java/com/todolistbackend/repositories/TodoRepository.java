package com.todolistbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todolistbackend.Models.TodoList;

@Repository
public interface TodoRepository extends CrudRepository<TodoList, Long>{

}
