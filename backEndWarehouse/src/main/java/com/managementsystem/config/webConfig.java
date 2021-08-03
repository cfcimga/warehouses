package com.managementsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class webConfig implements WebMvcConfigurer {

    private CrosInterceptor crosInterceptor;

    //构造方法
    public webConfig(CrosInterceptor crosInterceptor){
        this.crosInterceptor = crosInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","HEAD","DELETE","OPTIONS")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600)
                .allowedHeaders("*");
    }
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }
    //        excludePath.add("/static/**");
//        excludePath.add("/*/headPortrait/*.jpeg");
//        excludePath.add("/*/headPortrait/*.png");
//        excludePath.add("/*/headPortrait/*.jpg");
//        excludePath.add("/*/headPortrait/*.gif");
//        excludePath.add("/assets/**");  //静态资源
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了登录(此时还没token)和静态资源，其他都拦截
        excludePath.add("/user/findUserByIdAndPassword");  //登录
        excludePath.add("/headPortrait/**");  //静态资源
        registry.addInterceptor(crosInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("图片配置生效");
//        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\headPortrait\\";
//        System.out.println(filePath);
//        registry.addResourceHandler("/headPortrait/**").addResourceLocations("file:"+filePath);
//    }
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler请求路径
        //addResourceLocations 在项目中的资源路径
        //setCacheControl 设置静态资源缓存时间
            registry.addResourceHandler("/headPortrait/**").addResourceLocations("classpath:/static/headPortrait/");
            WebMvcConfigurer.super.addResourceHandlers(registry);
        }

}
