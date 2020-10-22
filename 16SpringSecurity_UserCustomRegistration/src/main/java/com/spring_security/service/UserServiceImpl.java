package com.spring_security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_security.dao.RoleDao;
import com.spring_security.dao.UserDao;
import com.spring_security.entity.Role;
import com.spring_security.entity.User;
import com.spring_security.model.CrmUser;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public User findUserByUsername(String name) {
		return userDao.findUserByUsername(name);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		user.setUsername(crmUser.getUsername());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstname(crmUser.getFirstname());
		user.setLastName(crmUser.getLastName());
		user.setEmailId(crmUser.getEmailId());
		user.setRoles(Arrays.asList(roleDao.findRoleByName(crmUser.getRoles())));//user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		userDao.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalids usernames or passwords");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority>mapRolesToAuthorities(Collection<Role> roles) {
	return roles.stream().map(role -> new
	SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
