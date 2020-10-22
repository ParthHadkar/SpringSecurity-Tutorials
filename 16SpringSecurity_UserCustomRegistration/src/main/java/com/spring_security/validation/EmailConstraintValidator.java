package com.spring_security.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

	private String[] emails;
	private Pattern pattern;
	private Matcher matcher;
	private static final String EmailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	// "\\b[a-zA-z0-9._%+-]{4,32}+@[a-zA-z]{5,32}+\\.[a-zA-z]{2,4}\\b"
	
	@Override
	public void initialize(ValidEmail constraintAnnotation) {
		//emails = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String emailVal, ConstraintValidatorContext context) {
		boolean valid = false;
		pattern = Pattern.compile(EmailPattern);
		if(emailVal != null) {
			matcher = pattern.matcher(emailVal);
			valid = matcher.matches();
		}
		return valid;
	}

}
