package com.spring_security.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring_security.entity.Role;
import com.spring_security.entity.User;

@Repository
public class RoleDaoImpl implements RoleDao{

	// inject session factory dependency
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Role[] findRoleByName(String[] roleNames) {
		Role[] theRoles = new Role[roleNames.length];
		Role theRole = null;
		try {
			// get current hibernate session
			Session session = sessionFactory.getCurrentSession();
			// create query to get user using username
			for(int i=0;i<roleNames.length;i++) {
			Query<Role>  query = session.createQuery("FROM Role r WHERE r.name=:rname",Role.class);
			query.setParameter("rname",roleNames[i]);
			theRole = (Role) query.getSingleResult();
			theRoles[i] = theRole;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theRoles;
	}

}
