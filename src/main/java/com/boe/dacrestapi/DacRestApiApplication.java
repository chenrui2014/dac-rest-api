package com.boe.dacrestapi;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
//public class DacRestApiApplication extends WebMvcConfigurationSupport {
public class DacRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DacRestApiApplication.class, args);
	}
	
//	
//	/**
//	 * 使用bean方式注入fastjson解析器
//	 *
//	 * @return
//	 */
//	@Bean
//	public HttpMessageConverters fastJsonHttpMessageConverters() {
//		// 创建fastjson对象
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//		FastJsonConfig confg = new FastJsonConfig();
//		// 设置是否需要格式化
//		confg.setSerializerFeatures(SerializerFeature.PrettyFormat,
//				SerializerFeature.WriteNullListAsEmpty,
//				SerializerFeature.WriteNullStringAsEmpty, 
//				SerializerFeature.WriteNullBooleanAsFalse,
//				SerializerFeature.DisableCircularReferenceDetect, 
//				SerializerFeature.WriteMapNullValue);
//		converter.setFastJsonConfig(confg);
//		return new HttpMessageConverters(converter);
//	}

}
