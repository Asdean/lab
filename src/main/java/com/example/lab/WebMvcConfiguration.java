package com.example.lab;

import com.example.lab.interceptor.AdminInterceptor;
import com.example.lab.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/lab/**")
                .excludePathPatterns("/lab/teacher/register")
                .excludePathPatterns("/lab/login");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/lab/admin/**")
                .excludePathPatterns("/lab/teacher/register")
                .excludePathPatterns("/lab/login");
    }
}
