package com.spring_security.dao;

import com.spring_security.entity.User;

public interface UserDao {
	
	public User findUserByUsername(String name); 
	public void save(User user);
	
}
