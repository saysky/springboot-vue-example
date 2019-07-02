package com.example.sens.config;

import com.example.sens.Interceptor.SecurityInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * <pre>
 *     拦截器，资源路径配置
 * </pre>
 *
 * @author : saysky
 * @date : 2018/1/2
 */
@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.sens.controller")
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true, encoding = "UTF-8")
public class MvcConfig implements WebMvcConfigurer {


    @Bean
    SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

//    /**
//     * 配置静态资源路径
//     *
//     * @param registry registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/templates/home/")
//                .addResourceLocations("classpath:/robots.txt");
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:///" + System.getProperties().getProperty("user.home") + "/sens/upload/");
//        registry.addResourceHandler("/favicon.ico")
//                .addResourceLocations("classpath:/static/images/favicon.ico");
//        registry.addResourceHandler("/backup/**")
//                .addResourceLocations("file:///" + System.getProperties().getProperty("user.home") + "/sens/backup/");
//    }
//

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器按照顺序执行
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/api/message/**")
                .addPathPatterns("/api/server/**")
                .excludePathPatterns("/api/user/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
