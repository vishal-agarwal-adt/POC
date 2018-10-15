package com.adt.sample.devops.config;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("file:${spring.config.location}/restApi-sample-app.properties")
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.adt.sample.devops.dao"),
		@ComponentScan("com.adt.sample.devops.service") })
public class AppConfig {

	@Autowired
	private Environment env;

	/*
	 * @Bean public LocalSessionFactoryBean getSessionFactory() {
	 * LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	 * 
	 * Properties props = new Properties(); // Setting JDBC properties
	 * props.put(DRIVER, env.getProperty("mysql.driver")); props.put(URL,
	 * env.getProperty("mysql.url")); props.put(USER,
	 * env.getProperty("mysql.user")); props.put(PASS,
	 * env.getProperty("mysql.password")); props.put(DIALECT,
	 * env.getProperty("mysql.dialect"));
	 * 
	 * // Setting Hibernate properties props.put(SHOW_SQL,
	 * env.getProperty("hibernate.show_sql")); props.put(HBM2DDL_AUTO,
	 * env.getProperty("hibernate.hbm2ddl.auto"));
	 * 
	 * // Setting C3P0 properties props.put(C3P0_MIN_SIZE,
	 * env.getProperty("hibernate.c3p0.min_size")); props.put(C3P0_MAX_SIZE,
	 * env.getProperty("hibernate.c3p0.max_size"));
	 * props.put(C3P0_ACQUIRE_INCREMENT,
	 * env.getProperty("hibernate.c3p0.acquire_increment")); props.put(C3P0_TIMEOUT,
	 * env.getProperty("hibernate.c3p0.timeout")); props.put(C3P0_MAX_STATEMENTS,
	 * env.getProperty("hibernate.c3p0.max_statements"));
	 * 
	 * factoryBean.setHibernateProperties(props);
	 * factoryBean.setPackagesToScan("com.adt.sample.devops.model");
	 * 
	 * return factoryBean; }
	 * 
	 * @Bean public HibernateTransactionManager getTransactionManager() {
	 * HibernateTransactionManager transactionManager = new
	 * HibernateTransactionManager();
	 * transactionManager.setSessionFactory(getSessionFactory().getObject()); return
	 * transactionManager; }
	 */

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.adt.sample.devops.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("mysql.driver"));
		dataSource.setUrl(env.getProperty("mysql.url"));
		dataSource.setUsername(env.getProperty("mysql.user"));
		dataSource.setPassword(env.getProperty("mysql.password"));
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("mysql.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", true);
		properties.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}
