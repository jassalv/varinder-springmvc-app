package com.spring.firstthymeleafapp.interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * The accessLogsInterceptor for all microservices. It retrieves the client-metadata
 * from the requestHeaders and persists it in logs. In near future the support for persisting the logs
 * into a persistent store will be turned on as well. 
 */
@Component
public class AccessLogsHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogsHandlerInterceptor.class);
    private static final String REQUEST_URL_STR_CONSTANT= "Request URL::";
    //
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("entered preHandle");
        // log the basic perf metrics
        logEntryTimings(request);
        //
        logger.debug("exited preHandle");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("entered afterCompletion");
        // basic perf metrics logs
        logExitTimings(request);
        //
        logger.debug("exited afterCompletion");
    }

    private void logExitTimings(HttpServletRequest request) {
        long startTime = (Long) request.getAttribute("startTime");
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: End Time=" , System.currentTimeMillis());
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: Time Taken=" , (System.currentTimeMillis() - startTime));
    }

    private void logEntryTimings(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: Start Time=" , System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
    }
}