package com.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/Login")
	public String loginPage() {
		//return "plain-Login";
		return "bootstrap-login";
	}
}
