package com.spring_security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

//@Configuration
//@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Add our user for in memory authentication
		/*		UserBuilder users = User.withDefaultPasswordEncoder();
	auth.inMemoryAuthentication()
		.withUser(users.username("parth").password("Test@123").roles("EMPLOYEE"))
		.withUser(users.username("omkar").password("Test@123").roles("EMPLOYEE","MANAGER"))
		.withUser(users.username("nikhil").password("Test@123").roles("EMPLOYEE","ADMIN"));*/
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http  
		.authorizeRequests()  
		.anyRequest().authenticated()  
		.and()  
		.formLogin()  
		.and()  
		.httpBasic(); */ 
		http.authorizeRequests() //.anyRequest().authenticated()
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/leaders/**").hasRole("MANAGER")
		.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/Login")
		.loginProcessingUrl("/autenticateUser")
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	
	
}
