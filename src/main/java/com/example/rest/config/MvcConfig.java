package com.example.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/error-curiers").setViewName("error-curiers");
        registry.addViewController("/order").setViewName("orders");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/curiers").setViewName("curiers");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
            "/img/**",
            "/css/**",
            "/js/**",
            "/static/**"
        ).addResourceLocations(
            "classpath:/static/img/",
            "classpath:/static/css/",
            "classpath:/static/js/",
            "classpath:/static/"
        );
    }
}
