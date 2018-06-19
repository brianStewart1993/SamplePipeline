package com.ete.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ete.models.User;
import com.ete.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public List<User> getAllUsers()
	{
		log.info("Getting all users");
		return userService.getAllUsers();
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/users/{id}")
	public ResponseEntity<?> getUsers(@PathVariable Integer id)
	{
		User user = userService.getUser(id);
		if(userService.getUser(id) != null)
		{
			log.info("Getting user with id: ", id);
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		}
		
		else
		{
			log.error("User not found with id: ", id);
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.POST, value="/users/add")
	public void addUser(@RequestBody User user)
	{
		log.info("Adding user");
		userService.addUser(user);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.PUT, value="/users/update")
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{
		String message;
		if(userService.getUserByUsernameAndId(user.getUsername(), user.getId())== "[]")
		{
		if(userService.updateUser(user) != null)
		{
			log.info("Updating user");
			message = userService.updateUser(user);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		else
		{
			log.error("Error updating user");
			message = "Error updating user!";
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			
		}
		}
		else
		{
			log.error("Username already taken");
			message = "Username already taken!";
			return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.DELETE, value="/users/delete/{id}")
	public String deleteUser(@PathVariable Integer id)
	{
		log.info("Deleting user with id: ", id);
		 return userService.deleteUser(id);
	
	
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/users/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> Login(@RequestParam("username") String username, @RequestParam("password") String password){
		
	int namelength = username.length();
	int passwordlength = password.length();
	String result;
	if(namelength <= 25 && passwordlength <=25 && namelength >= 8 && passwordlength >= 8)
	{
		log.info("User successfully logged in");
		String message = userService.login(username, password);
		if(message != "Username or Password Incorrect!")
		result = "User Login Successful!";
		else result = "Username or Password Incorrect!";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	else
	{
		log.error("Username or Password length should be between 8 to 25 characters!");
		 result = "Username or Password length should be between 8 to 25 characters!";
		 return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
		
	}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.POST, value="/users/addCustom")
	public ResponseEntity<?>  register(@RequestBody User user)
	{
		int nameLength, passwordLength = 0;
		nameLength = user.getUsername().length();
		passwordLength = user.getPassword().length();
		String message;
		
		if(user.getUsername()!= null && !user.getUsername().isEmpty() && !StringUtils.containsWhitespace(user.getUsername()) && !StringUtils.containsWhitespace(user.getPassword()))
		{
		if(userService.getUserByUsername(user.getUsername())!="[]")
		{
			log.error("Username already taken");
		 message = "Username already taken";
		 return new ResponseEntity<>(message, HttpStatus.BAD_GATEWAY);
		}
		else
		{
			if(nameLength <= 25 && passwordLength <=25 && nameLength >= 8 && passwordLength >= 8)
			{
				log.info("User created successfully");
			message = userService.Register(user.getUsername(), user.getPassword());
			return new ResponseEntity<>(message, HttpStatus.CREATED);
			}
			
			else 
				{
				message = "Username or Password length should be between 8 to 25 characters!";
				log.error(message);
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
				}
			
		}
		}
		else
		{
			message = "Username or Password must not be empty or contain spaces";
			log.error(message);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.GET, value = "/users/GetByUsername/{username}")
	public ResponseEntity<?> getUsersByUsername(@PathVariable String username)
	{
	    String user = userService.getUserByUsername(username);
		if(userService.getUserByUsername(username) != null)
		{
			log.info("Got user with username: ", username);
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		}
		
		else
		{
			log.error("User not found with username: ", username);
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
}
