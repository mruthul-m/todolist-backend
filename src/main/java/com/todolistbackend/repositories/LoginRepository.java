package com.todolistbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.todolistbackend.Models.User;

public interface LoginRepository extends CrudRepository<User, Long> {

}
