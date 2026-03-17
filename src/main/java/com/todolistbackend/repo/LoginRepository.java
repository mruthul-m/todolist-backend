package com.todolistbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.todolistbackend.Model.User;
import com.todolistbackend.dto.UserLoginDto;

public interface LoginRepository extends CrudRepository<User, Long> {
	
	@Query("""
			SELECT new com.todolistbackend.dto.UserLoginDto(u.name, u.password)
			FROM User u
			WHERE u.name = :username
			""")
	Optional<UserLoginDto> findByName(@Param("username") String username);
	
	boolean existsByName(String username);
}
