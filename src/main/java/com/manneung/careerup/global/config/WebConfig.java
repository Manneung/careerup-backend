package com.manneung.careerup.global.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://3.36.230.165/", "https://careerup.netlify.app/","http://localhost/")
                //.allowedOrigins("*")
                //.allowedOrigins("https://careerup.netlify.app/")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "HEAD", "OPTIONS")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3000);
    }
}