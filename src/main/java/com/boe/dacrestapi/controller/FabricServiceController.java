package com.boe.dacrestapi.controller;

import java.io.IOException;
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
import org.springframework.http.HttpStatus;
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
import com.boe.dacrestapi.service.FabricService;
import com.boe.dacrestapi.utils.MessageDigestUtils;
import com.boe.dacrestapi.vo.Photo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class FabricServiceController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${fabric.servicePath}")  
    private String fabricServicePath;  
	@Autowired
	private FabricService fabricService;
	
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
	@RequestMapping(value="/bci", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getBCI() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 如果为空则不输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 对于空的对象转json的时候不抛出错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用序列化日期为timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 禁用遇到未知属性抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 视空字符传为null
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 低层级配置
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 允许属性名称没有引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 取消对非ASCII字符的转码
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        
		ResponseEntity<String> entity = fabricService.getBCI();
		Map<String, Object> body = new HashMap<>();
		if(entity != null) {
        	try {
    			JsonNode jsonNode = objectMapper.readTree(entity.getBody());
    	        JsonNode values = jsonNode.with("values");
    	        JsonNode bcInfo = values.with("bcInfo");
    	        JsonNode bci = bcInfo.with("BCI");
    	        int height = bci.get("height") == null ? 0 : bci.get("height").asInt();
    	        String currentBlockHash = bci.get("currentBlockHash") == null ? "" : bci.get("currentBlockHash").asText();
    	        String previousBlockHash = bci.get("previousBlockHash") == null ? "" : bci.get("previousBlockHash").asText();
    	        String endorser = bcInfo.get("Endorser") == null ? "" : bcInfo.get("Endorser").asText();
    	        body.put("height",height);
    	        body.put("currentBlockHash",currentBlockHash);
    	        body.put("previousBlockHash",previousBlockHash);
    	        body.put("endorser",endorser);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		return new ResponseEntity<>(body, HttpStatus.OK);
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
