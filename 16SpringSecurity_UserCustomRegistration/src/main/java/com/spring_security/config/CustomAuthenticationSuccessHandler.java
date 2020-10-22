package com.spring_security.config;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.spring_security.entity.User;
import com.spring_security.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		try {
			logger.info("\n>>>> In CustomAuthenticationSuccessHandler\n");
			String userName = authentication.getName();
			logger.info(">>>> userName: "+userName);
			User user = userService.findUserByUsername(userName);
			logger.info(">>>> get session and add user attribute ");
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			logger.info(">>>> forwarding to home page");
			response.sendRedirect(request.getContextPath()+"/");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
