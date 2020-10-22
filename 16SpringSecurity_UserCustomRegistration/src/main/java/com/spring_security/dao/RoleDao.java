package com.spring_security.dao;

import com.spring_security.entity.Role;

public interface RoleDao {
	
	public Role[] findRoleByName(String[] roleNames);
}
