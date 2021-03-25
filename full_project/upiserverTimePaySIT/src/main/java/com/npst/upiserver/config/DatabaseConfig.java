package com.npst.upiserver.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.npst.upiserver.util.AESEncryptionUtility;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource(DataSourceProperties properties) {
		DataSource dataSource = null;
		try {
			properties.setPassword(
					AESEncryptionUtility.decrypt(properties.getPassword(), AESEncryptionUtility.secretKeys));
			dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in db configuration {}", e);
			System.exit(1);
		}
		return dataSource;
	}

}
