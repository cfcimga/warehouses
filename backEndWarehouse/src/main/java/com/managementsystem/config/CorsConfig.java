package com.managementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//原来的处理跨域的类，现在由webConfig来处理跨域
//@Configuration
public class CorsConfig  implements WebMvcConfigurer{   //implements WebMvcConfigurer
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                // 设置允许跨域请求的域名
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET","POST","PUT","HEAD","DELETE","OPTIONS")
//                // 是否允许证书（cookies）
//                .allowCredentials(true)
//                // 跨域允许时间
//                .maxAge(3600)
//                .allowedHeaders("*");
//
//
//    }


}
