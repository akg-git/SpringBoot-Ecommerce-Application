package edu.ecomm.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecomm.practice.entity.User;
import edu.ecomm.practice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService service;
	

	@GetMapping("/home")
    public String greet(HttpServletRequest request) {
		System.out.println("Session ID: "+request.getSession().getId() + "\nSession Create Time: "+request.getSession().getCreationTime()+ "\n");
        return "Welcome to User Portal";
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
    	System.out.println("Logging in ... user: "+user);
        return service.verify(user);
    }
    
    @GetMapping("/userlist")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> userList = service.getAllUsers();
		return new ResponseEntity<List<User>> (userList, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getProductById(@PathVariable Integer userId){
		
		User user = service.getUserById(userId);
		
		if(user != null)
			return new ResponseEntity<> (user, HttpStatus.OK);
		else
			return new ResponseEntity<> (user, HttpStatus.NOT_FOUND);
	}
	
}
