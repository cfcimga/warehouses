package com.managementsystem.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置拦截器
//@Configuration
public class stopConfig implements WebMvcConfigurer {

//    @Resource
//    private CrosInterceptor cros;
//
//    /* (non-Javadoc)
//     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(cros).addPathPatterns("/**");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }

}
