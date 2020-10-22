package com.spring_security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.spring_security.entity.User;
import com.spring_security.model.CrmUser;

public interface UserService extends UserDetailsService{

	public User findUserByUsername(String name); 
	public void save(CrmUser crmUser);
	
}
