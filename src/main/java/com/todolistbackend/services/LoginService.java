package com.todolistbackend.services;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistbackend.Models.User;
import com.todolistbackend.repositories.LoginRepository;


@Service
public class LoginService {
	
	static Logger log = Logger.getLogger("Login Logger");
	
	@Autowired
	LoginRepository loginRepo;
	
	public boolean saveUser(User user) {
		if (loginRepo.existsById(user.getId())) {
			return false;
		}
		loginRepo.save(user);
		return true;
		
	}
	
	public User getSingleUser(Long id) {
		try {
			return loginRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			log.severe("No such Id present in the Database");
		}		
		return (User) null;
	}

}
