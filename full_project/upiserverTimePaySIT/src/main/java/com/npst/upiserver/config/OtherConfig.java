package com.npst.upiserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class OtherConfig {
	private static final Logger log = LoggerFactory.getLogger(OtherConfig.class);

	@Bean
	ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		log.info("objectMapper={}", objectMapper);
		return objectMapper;
	}

}
