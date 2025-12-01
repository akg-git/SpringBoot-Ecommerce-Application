package edu.ecomm.practice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ecomm.practice.entity.User;
import edu.ecomm.practice.repository.UserRepository;
import edu.ecomm.practice.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jservice;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	@Override
	public String verify(User user) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));	// authenticate token using username and password

		if (auth.isAuthenticated())
			//generate token
			return jservice.generateToken(user);
		return "Login Failed!";
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer userId) {
		return userRepository.findById(userId).orElse(null);
	}

	
	
}
