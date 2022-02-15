package com.kycni.community.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kycni
 * @date 2022/2/15 15:00
 */
@Component
public class AlphaInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AlphaInterceptor.class);
    
    // 在调用Controller之前执行 （处理请求）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle被调用了: " + handler.toString());
        return true;
    }
    
    // 在调用Controller之后执行 （处理响应）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle被调用了: " + handler.toString());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 在模板引擎TemplateEngine之后，最后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("afterCompletion: " + handler.toString());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
