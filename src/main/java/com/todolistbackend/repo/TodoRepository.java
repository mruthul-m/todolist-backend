package com.todolistbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolistbackend.Model.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{

}
