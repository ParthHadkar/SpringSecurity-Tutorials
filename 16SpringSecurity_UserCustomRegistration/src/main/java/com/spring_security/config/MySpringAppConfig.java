package com.spring_security.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.spring_security")
@PropertySource("classpath:persistence-mysql.properties")//({"classpath:persistence-mysql.properties"})
public class MySpringAppConfig  implements WebMvcConfigurer {
	
	// setup a variable to hold the properties
	@Autowired
	private Environment env;
	
	// setup logger for diagnoistics
	private Logger logger = Logger.getLogger(getClass().getName());

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	// Define a bean for our security datasource
	@Bean
	public DataSource securityDataSource() {
		// create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		// set the driver class
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			new RuntimeException(exc);
		}
		// log the connection props
		logger.info(">>>> JDBC Url:"+env.getProperty("jdbc.url"));
		logger.info(">>>> JDBC User:"+env.getProperty("jdbc.user"));
		// set the database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		// set the connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		return securityDataSource;
	}
	
	// need helper method to read environment property and convert to int
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int propInt = Integer.parseInt(propVal);
		return propInt;
	}
	
	// need helper method to set hibernate properties
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return properties;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// create session Factory 
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		// set the Properties
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		// setup transaction manager based on session factory
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	
	
	
}
