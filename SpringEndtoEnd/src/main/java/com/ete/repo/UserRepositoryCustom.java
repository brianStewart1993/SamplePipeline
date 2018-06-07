package com.ete.repo;


public interface UserRepositoryCustom {
	
		String Login(String username, String password);
		String Register(String username, String password);
		String getUserByUsername(String username);
		String getUserByUsernameAndId(String username, Integer id);
}
