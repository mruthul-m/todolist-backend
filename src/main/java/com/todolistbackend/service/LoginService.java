package com.todolistbackend.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolistbackend.Model.User;
import com.todolistbackend.dto.UserRequestDto;
import com.todolistbackend.dto.UserResponseDto;
import com.todolistbackend.repo.LoginRepository;

@Service
public class LoginService implements UserDetailsService {

	static Logger log = Logger.getLogger("Login Logger");
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	final LoginRepository loginRepo;

	@Autowired
	public LoginService(LoginRepository loginRepo) {
		this.loginRepo = loginRepo;
	}

	public UserResponseDto saveUser(UserRequestDto userDTO) {

		if (!loginRepo.existsByName(userDTO.getName())) {
			User user = new User();
			user.setName(userDTO.getName());
			user.setPassword(encoder.encode(userDTO.getPassword()));
			User saved = loginRepo.save(user);

			UserResponseDto responseDto = new UserResponseDto();
			responseDto.setUserId(saved.getId());
			responseDto.setUserName(saved.getName());

			return responseDto;
		}
		return (UserResponseDto) null;
	}

	public UserResponseDto getSingleUser(Long id) {
		try {
			User user = loginRepo.findById(id).get();
			UserResponseDto resDTO = new UserResponseDto();
			resDTO.setUserId(user.getId());
			resDTO.setUserName(user.getName());
			return resDTO;
		} catch (NoSuchElementException e) {
			log.severe("No such Id present in the Database");
		}
		return (UserResponseDto) null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userDetails = loginRepo.findByName(username);

		return userDetails.get();

	}

}
