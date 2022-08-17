package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowedOrigins("http://localhost:3000");
//                .allowedOrigins("devtools-lllj9dvqk-green9930.vercel.app");
    }
}