package com.todolistbackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todolistbackend.Model.TodoList;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long>{
	
	@Query("""
			SELECT t
			FROM TodoList t
			WHERE t.fk_user_id = :userid
			""")
	List<TodoList> findByUserId(@Param("userid") Long id);

}
