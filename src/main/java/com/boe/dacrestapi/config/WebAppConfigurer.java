package com.boe.dacrestapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

	@Value("${web.upload-path}")
	private String UPLOADED_FOLDER;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/assets/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/assets/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+ UPLOADED_FOLDER);
    }
}
