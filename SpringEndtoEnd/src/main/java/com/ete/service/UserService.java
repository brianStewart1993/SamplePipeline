package com.ete.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ete.models.User;
import com.ete.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
	public User getUser(Integer id)
	{
		return userRepository.findOne(id);
	}
	
	public void addUser(User user) {

		userRepository.save(user);
			
	}

	public String updateUser(User user) {
		userRepository.save(user);
		return "User updated successfully";
		
	}

	public String deleteUser(Integer id) {
	userRepository.delete(id);
		return "User successfully deleted";
	}
	
	public String login(String uname, String pw)
	{
		return userRepository.Login(uname, pw);
	}
	
	public String getUserByUsername(String uname)
	{
		return userRepository.getUserByUsername(uname);
	}
	
	public String getUserByUsernameAndId(String uname, Integer id)
	{
		return userRepository.getUserByUsernameAndId(uname, id);
	}
	
	
	public String Register(String uname, String pw)
	{
		return userRepository.Register(uname, pw);
	}
}
