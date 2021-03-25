package com.npst.middleware.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.npst.middleware.util.AESEncryptionUtility;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Sumaiya Ahmad
 * @since 15/06/2017
 */

@Configuration
@EnableTransactionManagement
//@PropertySource(value = "file:/opt/npst/middleware_db_file/application.properties")
public class DatabaseConfig {
	
	private static final Logger						log	= LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Autowired
	private Environment								env;
	
	@Autowired
	private DataSource								dataSource;
	
	@Autowired
	private LocalContainerEntityManagerFactoryBean	entityManagerFactory;
	
	@Bean
	DataSource dataSource() {
		log.trace("Creating HikariCP data source", null, null);
		HikariConfig dataSourceConfig = new HikariConfig();
		try {
			log.info("");
			dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
			dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
			dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
			dataSourceConfig.setPassword(AESEncryptionUtility.decrypt(env.getRequiredProperty("db.password"), AESEncryptionUtility.secretKeys));
			dataSourceConfig.setConnectionTimeout(env.getRequiredProperty("db.connectiontimeout", Long.class));
			dataSourceConfig.setIdleTimeout(env.getRequiredProperty("db.idletimeout", Long.class));
			dataSourceConfig.setMaximumPoolSize(env.getRequiredProperty("db.maxpoolsize", Integer.class));
			dataSourceConfig.setMaxLifetime(env.getRequiredProperty("db.maxlifetime", Long.class));
			dataSourceConfig.setMinimumIdle(env.getRequiredProperty("db.minidleconnection", Integer.class));
			dataSourceConfig.addDataSourceProperty("cachePrepStmts", env.getRequiredProperty("db.cachePrepStmts"));
			dataSourceConfig.addDataSourceProperty("prepStmtCacheSize",
					env.getRequiredProperty("db.prepStmtCacheSize"));
			dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit",
					env.getRequiredProperty("db.prepStmtCacheSqlLimit"));
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s.toString());
		}
		log.info("HikariCP data source created successfully", getConfigDetailsForLogging(dataSourceConfig), null);
		return new HikariDataSource(dataSourceConfig);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		log.trace("Entry inside entity manager factor for HikariCP", null, null);
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		try {
			entityManagerFactory.setDataSource(dataSource);
			entityManagerFactory.setPackagesToScan(env.getProperty("entitymanager.packagesToScan"));
			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
			Properties additionalProperties = new Properties();
			additionalProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
			additionalProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
			if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
				additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
			}
			entityManagerFactory.setJpaProperties(additionalProperties);
			log.info("Exit from entity manager factor for HikariCP", null, null);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.error(s.toString());
		}
		return entityManagerFactory;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private String getConfigDetailsForLogging(final HikariConfig dataSourceConfig) {
		Properties prop = dataSourceConfig.getDataSourceProperties();
		
		return "DatabaseConfig [Connection Timeout=" + dataSourceConfig.getConnectionTimeout() + ", Idle Timeout="
				+ dataSourceConfig.getIdleTimeout() + ", Max Pool Size=" + dataSourceConfig.getMaximumPoolSize()
				+ ", Max Lifetime=" + dataSourceConfig.getMaxLifetime() + ", Min Idle Time="
				+ dataSourceConfig.getMinimumIdle() + ", cachePrepStmts=" + prop.getProperty("cachePrepStmts")
				+ ", prepStmtCacheSize=" + prop.getProperty("prepStmtCacheSize") + ", prepStmtCacheSqlLimit="
				+ prop.getProperty("prepStmtCacheSqlLimit") + "]";
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		log.trace("Entry inside transaction manager for HikariCP", null, null);
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		log.info("Exit from transaction manager for HikariCP", null, null);
		return transactionManager;
	}
}
