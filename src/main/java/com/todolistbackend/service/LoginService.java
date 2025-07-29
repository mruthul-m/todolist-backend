package com.todolistbackend.service;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolistbackend.Model.User;
import com.todolistbackend.Model.UserPrinciple;
import com.todolistbackend.repo.LoginRepository;


@Service
public class LoginService  implements UserDetailsService{
	
	static Logger log = Logger.getLogger("Login Logger");
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	

	final LoginRepository loginRepo;
	
	@Autowired
	public LoginService(LoginRepository loginRepo) {
		this.loginRepo = loginRepo;
	}
	
	public boolean saveUser(User user) {
		if (loginRepo.existsById(user.getId())) {
			return false;
		}
		user.setPassword(encoder.encode(user.getPassword()));
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = loginRepo.findByName(username);
		if (user == null) {
			System.out.println("User is not exist!");
			throw new UsernameNotFoundException("User is not found");
		}
		return new UserPrinciple(user);
	}

}
