//package com.boe.dacrestapi.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class ImgMvcConfig extends WebMvcConfigurerAdapter {
//	
//	@Value("${web.upload-path}")
//	private String UPLOADED_FOLDER;
// 
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:" + UPLOADED_FOLDER);
//        super.addResourceHandlers(registry);
//    }
//}