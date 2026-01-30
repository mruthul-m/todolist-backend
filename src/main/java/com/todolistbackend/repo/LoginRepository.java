package com.todolistbackend.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.todolistbackend.Model.User;

public interface LoginRepository extends CrudRepository<User, Long> {
	Optional<User> findByName(String username);
	boolean existsByName(String username);
}
