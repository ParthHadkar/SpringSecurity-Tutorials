package com.spring_security.dao;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring_security.entity.User;

@Repository
public class UserDaoImpl implements UserDao{

	// inject session factory dependency
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public User findUserByUsername(String name) {
		User theUser = null;
		try {
			// get current hibernate session
			Session session = sessionFactory.getCurrentSession();
			// create query to get user using username
			Query<User> query = session.createQuery("FROM User u WHERE u.username=:uname",User.class);
			query.setParameter("uname",name);
			theUser = (User) query.getSingleResult();
		} catch (NoResultException e) {
			theUser = null;
			//e.printStackTrace();
		}
		catch (Exception e) {
			theUser = new User();
			e.printStackTrace();
		}
		return theUser;
	}

	@Override
	public void save(User user) {
		try {
			// get current hibernate session
			Session session = sessionFactory.getCurrentSession();
			// save or update the user
			session.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
