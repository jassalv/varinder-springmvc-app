package com.spring.firstthymeleafapp.config;

import com.spring.firstthymeleafapp.interceptors.AccessLogsHandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * Sets up the accessLogsInterceptor to intercept specific paths
 *
 */
@Configuration
public class ApplicationConfigurerLocalAdapter extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurerLocalAdapter.class);
    @Autowired
    AccessLogsHandlerInterceptor accessLogsHandlerInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.warn("***** http block interceptor NOT setup *****");

        registry.addInterceptor(accessLogsHandlerInterceptor)
                .addPathPatterns("/expensetracker/**");
    }
}