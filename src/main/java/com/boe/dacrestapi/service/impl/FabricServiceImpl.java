package com.boe.dacrestapi.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boe.dacrestapi.service.FabricService;
import com.boe.dacrestapi.vo.Photo;

@Service
public class FabricServiceImpl implements FabricService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${fabric.servicePath}")  
    private String fabricServicePath; 
	
	@Override
	public Photo getOnePhoto(String photoId) {
		return this.restTemplate.getForObject(this.fabricServicePath + "/{photoId}", Photo.class, photoId);
	}

	@Override
	public List<Photo> getAllPhoto() {
		Photo[] photoList = this.restTemplate.getForObject(this.fabricServicePath, Photo[].class);
		return Arrays.asList(photoList);
	}

	@Override
	public Photo addPhoto(Photo photo) {
		return this.restTemplate.postForObject(this.fabricServicePath + "/1", null, Photo.class, photo);
	}

	@Override
	public ResponseEntity<String> addPhoto(HttpEntity<Map<String, Object>> requestEntity) {
		return this.restTemplate.exchange(this.fabricServicePath + "v1/photo/register", HttpMethod.POST, requestEntity, String.class);
	}

	@Override
	public ResponseEntity<String> buyPhoto(HttpEntity<Map<String, Object>> requestEntity) {
		return this.restTemplate.exchange(this.fabricServicePath + "v1/photo/buy", HttpMethod.POST, requestEntity, String.class);
	}

	@Override
	public ResponseEntity<String> getBCI() {
		return this.restTemplate.getForEntity(this.fabricServicePath + "v1/bc/info", String.class);
	}
	

}
