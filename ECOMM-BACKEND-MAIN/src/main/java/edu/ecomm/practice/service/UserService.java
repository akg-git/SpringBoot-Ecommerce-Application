package edu.ecomm.practice.service;


import java.util.List;

import org.springframework.stereotype.Service;

import edu.ecomm.practice.entity.User;

@Service
public interface UserService {

	User register(User user);

	String verify(User user);

	List<User> getAllUsers();

	User getUserById(Integer userId);

}
