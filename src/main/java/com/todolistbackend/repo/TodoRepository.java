package com.todolistbackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todolistbackend.Model.TodoList;
import com.todolistbackend.dto.TodosDto;

import jakarta.transaction.Transactional;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{
	
	
	@Query("""
			SELECT new com.todolistbackend.dto.TodosDto(t.taskHeading, t.taskContent, t.createdAt)
			FROM TodoList t
			WHERE t.fk_user_id = :userid
			""")
	List<TodosDto> findByUserId(@Param("userid") Long id);
	
	@Modifying
	@Transactional
	@Query("""
			DELETE FROM TodoList t WHERE t.fk_user_id = :userid
			""")
	int deleteAllByUserId(@Param("userid") Long id);

}
