package com.spring_security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.spring_security.service.UserService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

	//@Autowired
	//private DataSource securityDataSource;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	// Define Bcrypt Bean
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Define authenticationProvider Bean
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// use JDBC authentication
		//auth.jdbcAuthentication().dataSource(securityDataSource);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //.anyRequest().authenticated()
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/leaders/**").hasRole("MANAGER")
		.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/Login")
		.loginProcessingUrl("/autenticateUser")
		.successHandler(customAuthenticationSuccessHandler)
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	/*@Bean
	public UserDetailsManager userDetailsManager() {
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		
		jdbcUserDetailsManager.setDataSource(securityDataSource);
		
		return jdbcUserDetailsManager; 
	}*/
	
}
