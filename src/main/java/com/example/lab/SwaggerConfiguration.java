package com.example.lab;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket platformApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.lab")) //指定提供接口所在的基包
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(
                new ApiKey("Authorization", "token", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "认证权限");
        return Lists.newArrayList(
                new SecurityReference("Authorization", new AuthorizationScope[]{authorizationScope}));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("实验室预约系统 邓杰")
                .version("1")
                .termsOfServiceUrl("localhost:8080/")
                .description("demo api")
                .build();
    }
    /*@Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.lab")) //指定提供接口所在的基包
                .build();
    }

    *//**
     * 该套 API 说明，包含作者、简介、版本、host、服务URL
     * @return
     *//*
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("实验室预约系统 邓杰")
                .version("1")
                .termsOfServiceUrl("localhost:8080/")
                .description("demo api")
                .build();
    }*/
}
