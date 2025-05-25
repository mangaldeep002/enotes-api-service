package com.enote.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProjectConfig {

    @Bean
    ModelMapper modelMapper() {
		return new ModelMapper();
	}
    
    @Bean
    AuditorAware <Integer> auditAware() {
    	return new AuditAwareConfig();
    }
}
