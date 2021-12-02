//package com.safedog.cloudnet.config.web;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author ycs
// * @description
// * @date 2021/10/25 13:49
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
////    @Value("${spring.servlet.multipart.location}")
////    private String uploadFileUrl;
//
//
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////                .allowedOrigins("*")
////                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
////                .maxAge(3600)
////                // 是否允许发送Cookie
////                .allowCredentials(true)
////                .allowedHeaders("*");
////    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 静态文件
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        // swagger
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        // 上传文件
////        registry.addResourceHandler("/file/**").addResourceLocations("file:/" + uploadFileUrl);
//    }
//
//}
