package com.todolistbackend.repo;

import org.springframework.data.repository.CrudRepository;

import com.todolistbackend.Model.User;

public interface LoginRepository extends CrudRepository<User, Long> {
	User findByName(String username);
}
