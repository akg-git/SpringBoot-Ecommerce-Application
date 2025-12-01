package edu.ecomm.practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.ecomm.practice.entity.User;
import edu.ecomm.practice.entity.UserPrinciple;
import edu.ecomm.practice.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			System.out.println(username+" Not Found!!");
			throw new UsernameNotFoundException(username+" Not Found!!");
		}
				
		return new UserPrinciple(user);
	}

}
