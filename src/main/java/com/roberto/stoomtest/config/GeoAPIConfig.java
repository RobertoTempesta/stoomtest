package com.roberto.stoomtest.config;

import com.google.maps.GeoApiContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoAPIConfig {
    
    @Value("${api.key}")
	private String KEY;

    @Bean
	public GeoApiContext geoApiContext() {
		return new GeoApiContext.Builder().apiKey(KEY).build();
	}
}
