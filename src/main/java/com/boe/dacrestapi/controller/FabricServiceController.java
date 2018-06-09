package com.boe.dacrestapi.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.boe.dacrestapi.utils.MessageDigestUtils;
import com.boe.dacrestapi.vo.Photo;

@RestController
public class FabricServiceController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${fabric.servicePath}")  
    private String fabricServicePath;  
	
	@RequestMapping(value="/photo", method=RequestMethod.GET)
	public Photo getPhoto(@RequestParam("photoId") long photoId) {
		Photo photo = this.restTemplate.getForObject(this.fabricServicePath + "/{photoId}", Photo.class, photoId);
		return photo;
	}
	
	@RequestMapping(value="/photoList", method=RequestMethod.GET)
	public List<Photo> getAllPhoto(){
		Photo[] photoList = this.restTemplate.getForObject(this.fabricServicePath, Photo[].class);
		return Arrays.asList(photoList);
	}
	
	@RequestMapping(value="/addPhoto", method=RequestMethod.GET)
	public ResponseEntity<String> addPhoto() {
//		Photo photo = new Photo();
//		photo.setAuthor("chen");
//		photo.setCa_sn(MessageDigestUtils.sha384("chen"));
//		photo.setName("蒙娜丽莎的微笑");
//		photo.setHash(MessageDigestUtils.sha384("chen"));
//		photo.setPrice(15.09);
//		photo.setReferred(MessageDigestUtils.sha256("chen"));
//		photo.setReg_Time("2018-06-09 24:00：00");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("hash", "hello,world");
		params.put("name", "蒙娜丽莎的微笑");
		params.put("author", "chen");
		params.put("reg_Time", 123445566);
		params.put("ca_sn", "hello,world");
		params.put("price", 12);
		params.put("referred", "hello,world");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(params, headers);  
//		Object fd = this.restTemplate.postForObject(this.fabricServicePath + "v1/photo/register", null, Object.class,photo);
//		System.out.println(fd.toString());
		ResponseEntity<String> response = this.restTemplate.exchange(this.fabricServicePath + "v1/photo/register", HttpMethod.POST, requestEntity, String.class);
		System.out.println(response.getBody()); 
		return response;
	}
} 
