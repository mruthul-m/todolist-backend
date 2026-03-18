package com.todolistbackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.dto.TodosDto;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{
	
	
	@Query("""
			SELECT new com.todolistbackend.dto.TodosDto(t.taskHeading, t.taskContent, t.createdAt)
			FROM TodoList t
			WHERE t.fk_user_id = :userid
			""")
	List<TodosDto> findByUserId(@Param("userid") Long id);
	
	

}
