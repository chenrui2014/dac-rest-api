package com.boe.dacrestapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.boe.dacrestapi.vo.Photo;

public interface FabricService {

	Photo getOnePhoto(String photoId);
	List<Photo> getAllPhoto();
	
	Photo addPhoto(Photo photo);
	
	ResponseEntity<String> addPhoto(HttpEntity<Map<String, Object>> requestEntity);
	ResponseEntity<String> buyPhoto(HttpEntity<Map<String, Object>> requestEntity);
}
