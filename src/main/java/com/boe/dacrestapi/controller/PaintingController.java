package com.boe.dacrestapi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.FabricService;
import com.boe.dacrestapi.service.PaintingService;
import com.boe.dacrestapi.service.UserService;
import com.boe.dacrestapi.utils.MessageDigestUtils;
import com.boe.dacrestapi.vo.PaintingVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
public class PaintingController {

	@Autowired
	private PaintingService paintingService;
	@Autowired
	private UserService userService;
	@Autowired
	private FabricService fabricService;
	
	@RequestMapping(value="/paintings", method=RequestMethod.GET)
	public List<PaintingVO> getAllPaintings() {
		List<Painting> paintingList = paintingService.findAll();
		return convertPOJOList(paintingList);
	}
	@RequestMapping(value="/paintingsOrder", method=RequestMethod.GET)
	public List<PaintingVO> getAllPaintingsOrderByRegTime(){
		Sort sort = new Sort(Direction.DESC,"regTime");
		List<Painting> paintingList = paintingService.findAll(sort);
		return convertPOJOList(paintingList);
	}
	@RequestMapping(value="/paintingsPerPage", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getPaintingPerPage(@RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		Sort sort = new Sort(Direction.DESC,"regTime");
		Pageable pageable = new PageRequest(page,size,sort);
		Page<Painting> paintingPage = paintingService.findAllPaging(pageable);
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(paintingPage.getContent()));
		body.put("number", paintingPage.getNumber());
		body.put("size", paintingPage.getSize());
		body.put("totalElements", paintingPage.getTotalElements());
		body.put("totalPages", paintingPage.getTotalPages());
//		List<Transactions> tranList = new ArrayList<Transactions>();
//		while (tranIt.hasNext()) {
//			Transactions tran = tranIt.next();
//			tranList.add(tran);
//		}
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addPainting", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addPainting(@RequestBody PaintingVO paintingVO) {
		Painting painting2 = convertVO(paintingVO);
		String md5Str = MessageDigestUtils.md5(painting2.getAuthor()
				+painting2.getPaintName()
				+painting2.getPaintDes()
				+painting2.getPaintUrl()
				+painting2.getRegTime()
				+painting2.getPaintingPrice()
				+ new Long(painting2.getId()).toString()
				+ new Long(painting2.getUser() == null ? 0 : painting2.getUser().getId()).toString());
		painting2.setPaintHash(md5Str);
		painting2.setDepCerticateId(MessageDigestUtils.sha384(md5Str));
		painting2.setDigFingerPrint(MessageDigestUtils.sha256(md5Str));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("hash", painting2.getDigFingerPrint());
		params.put("name", painting2.getPaintName());
		params.put("author", painting2.getAuthor());
		params.put("reg_Time", painting2.getRegTime().getTime());
		params.put("ca_sn", painting2.getDepCerticateId());
		params.put("price", painting2.getPaintingPrice());
		if(painting2.getDenPainting() != null) {
			params.put("referred", painting2.getDenPainting().getPaintHash());
		}
		
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(params, headers);  
		
		ResponseEntity<String> entity = fabricService.addPhoto(requestEntity);
		
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
        if(entity != null) {
        	try {
    			JsonNode jsonNode = objectMapper.readTree(entity.getBody());
    	        JsonNode values = jsonNode.with("values");
    	        String txId = values.get("txId") == null ? "" : values.get("txId").asText();
    	        painting2.setTransactionId(txId);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }

		painting2 = paintingService.save(painting2);
		
		List<Painting> denPaintList = new ArrayList<Painting>();
		denPaintList.add(painting2);
		Painting p2 = painting2.getDenPainting();
		if(p2 != null) {
			denPaintList.add(p2);
			if(p2.getDenPainting() != null) {
				denPaintList.add(p2.getDenPainting());
			}
		}
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(denPaintList));
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/userPaintingsNoPage", method=RequestMethod.GET)
	public List<PaintingVO> getUserPaintingNoPage(@RequestParam(value = "userId")Long userId){
		
		List<Painting> paintingList = paintingService.findByUserIdNoPage(userId);
		return convertPOJOList(paintingList);
	}
	
	@RequestMapping(value="/userPaintingsPerPage", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUserPaintingPerPage(@RequestParam(value = "userId")Long userId, @RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		Pageable pageable = new PageRequest(page,size);
		Page<Painting> paintingPage = paintingService.findByUserId(userId, pageable);
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(paintingPage.getContent()));
		body.put("number", paintingPage.getNumber());
		body.put("size", paintingPage.getSize());
		body.put("totalElements", paintingPage.getTotalElements());
		body.put("totalPages", paintingPage.getTotalPages());
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	private PaintingVO convertPOJO(Painting painting) {
		PaintingVO paintingVO = new PaintingVO();
		paintingVO.setId(painting.getId());
		paintingVO.setAuthor(painting.getAuthor());
		Painting denPainting = painting.getDenPainting();
		if(denPainting == null) {
			paintingVO.setDenPaintHash("");
			paintingVO.setDenPaintId(0);
			paintingVO.setDenPaintName("");
		}else {
			paintingVO.setDenPaintHash(denPainting.getPaintHash());
			paintingVO.setDenPaintId(denPainting.getId());
			paintingVO.setDenPaintName(denPainting.getPaintName());
		}
		
		User user = painting.getUser();
		if(user == null) {
			paintingVO.setUserId(0);
			paintingVO.setUserName("");
		}else {
			paintingVO.setUserId(user.getId());
			paintingVO.setUserName(user.getUserName());
		}
		
		paintingVO.setDepCerticateId(painting.getDepCerticateId());
		paintingVO.setDigFingerPrint(painting.getDigFingerPrint());
		paintingVO.setGenFlag(painting.getGenFlag());
		paintingVO.setPaintHash(painting.getPaintHash());
		paintingVO.setPaintingPrice(painting.getPaintingPrice());
		paintingVO.setPaintName(painting.getPaintName());
		paintingVO.setTransactionId(painting.getTransactionId());
		paintingVO.setPaintDes(painting.getPaintDes());
		paintingVO.setPaintUrl(painting.getPaintUrl());
		paintingVO.setType(painting.getType());
		paintingVO.setStatus(painting.getStatus());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(painting.getRegTime());
		paintingVO.setRegTime(formattedDate);
		return paintingVO;
	}
	
	private Painting convertVO(PaintingVO paintingVO) {
		Painting painting = new Painting();
		painting.setId(painting.getId());
		painting.setAuthor(paintingVO.getAuthor());
		
		Optional<Painting> paintingOpt = paintingService.findById(paintingVO.getDenPaintId());

		painting.setDenPainting(paintingOpt.orElse(null));
		if(paintingOpt.map(p -> p.getDenPainting()).isPresent()) {
			painting.setGenFlag("3");
		}else {
			if(painting.getDenPainting() == null) {
				painting.setGenFlag("1");
			}else {
				painting.setGenFlag("2");
			}
		}
		
		Optional<User> userOpt = userService.findOne(paintingVO.getUserId());
		painting.setUser(userOpt.orElse(new User()));
		
		painting.setDepCerticateId(paintingVO.getDepCerticateId());
		painting.setDigFingerPrint(paintingVO.getDigFingerPrint());
		painting.setPaintHash(paintingVO.getPaintHash());
		painting.setPaintingPrice(paintingVO.getPaintingPrice());
		painting.setPaintName(paintingVO.getPaintName());
		
		painting.setTransactionId(paintingVO.getTransactionId());
		painting.setPaintDes(paintingVO.getPaintDes());
		painting.setPaintUrl(paintingVO.getPaintUrl());
		painting.setType(paintingVO.getType());
		
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		try {   
            ts = Timestamp.valueOf(paintingVO.getRegTime());     
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
		painting.setRegTime(ts);
		
		if(painting.getStatus() != null && !painting.getStatus().isEmpty()) {
			painting.setStatus(paintingVO.getStatus());
		}else {
			painting.setStatus("1");
		}
		
		
		return painting;
	}
	
	private List<Painting> convertVOList(List<PaintingVO> paintingVOs){
		List<Painting> paintingList = new ArrayList<Painting>();
		for(PaintingVO paintingVO:paintingVOs) {
			Painting painting = convertVO(paintingVO);
			paintingList.add(painting);
		}
		return paintingList;
	}
	
	private List<PaintingVO> convertPOJOList(List<Painting> pojoList){
		
		List<PaintingVO> paintingVOs = new ArrayList<PaintingVO>();
		for(Painting painting:pojoList) {
			PaintingVO paintingVO = convertPOJO(painting);
			paintingVOs.add(paintingVO);
		}
		return paintingVOs;
	}
}
