package com.boe.dacrestapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.FabricService;
import com.boe.dacrestapi.service.PaintingService;
import com.boe.dacrestapi.service.TransactionsService;
import com.boe.dacrestapi.service.UserService;
import com.boe.dacrestapi.utils.MessageDigestUtils;
import com.boe.dacrestapi.vo.PaintingVO;
import com.boe.dacrestapi.vo.TransactionsVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class TransactionsController {

	@Autowired
	private TransactionsService tranService;
	@Autowired
	private UserService userService;
	@Autowired
	private PaintingService paintingService;
	@Autowired
	private FabricService fabricService;
	
	@RequestMapping(value="/trans",method=RequestMethod.GET)
	public List<TransactionsVO> getAllTrans(){
		List<Transactions> tranList = tranService.findAll();
		return convertPOJOList(tranList);
	}
	@RequestMapping(value="/transPerPage",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllTransPaging(@RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		Pageable pageable = new PageRequest(page,size);
		Page<Transactions> tranPage = tranService.findAll(pageable);
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(tranPage.getContent()));
		body.put("number", tranPage.getNumber());
		body.put("size", tranPage.getSize());
		body.put("totalElements", tranPage.getTotalElements());
		body.put("totalPages", tranPage.getTotalPages());
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/userTransPerPage",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUserTransPaging(@RequestParam(value = "userId")Long userId, @RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		Pageable pageable = new PageRequest(page,size);
		Page<Transactions> tranPage = tranService.findTransactionsByUser(userId, pageable);
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(tranPage.getContent()));
		body.put("number", tranPage.getNumber());
		body.put("size", tranPage.getSize());
		body.put("totalElements", tranPage.getTotalElements());
		body.put("totalPages", tranPage.getTotalPages());
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addTran", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> buyPainting(@RequestBody TransactionsVO transactionsVO) {
		Transactions transactions = convertVO(transactionsVO);
		String md5Str = MessageDigestUtils.md5(transactionsVO.getGenTime()
				+transactionsVO.getId()
				+transactionsVO.getTranAmount()
				+transactionsVO.getInitiatorUserId()
				+transactionsVO.getReceiverUserId()
				+ transactionsVO.getPaintingId());
		transactions.setTranHash(MessageDigestUtils.sha256(md5Str));
		transactions.setRegBlock(MessageDigestUtils.sha1(md5Str));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("id", transactions.getTranHash());
		Painting painting = transactions.getPainting();
		if(painting != null) {
			params.put("photoHash", painting.getPaintHash());
		}
		
		User buyer = transactions.getInitiatorUser();
		if(buyer != null) {
			params.put("buyer", buyer.getId());
		}
		
		User seller = transactions.getReceiverUser();
		if(seller != null) {
			params.put("seller", seller.getId());
		}
		params.put("price", transactions.getTranAmount());
		
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(params, headers);  
		
		ResponseEntity<String> entity = fabricService.buyPhoto(requestEntity);
		
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
    	        transactions.setTranId(txId);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }

		
        transactions = tranService.save(transactions);
		
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJO(transactions));
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	private TransactionsVO convertPOJO(Transactions tran) {
		TransactionsVO tranVO = new TransactionsVO();
		tranVO.setId(tran.getId());
		tranVO.setGenTime(tran.getGenTime());
		User initiator = tran.getInitiatorUser();
		if(initiator == null) {
			tranVO.setInitiatorUserId(0);
			tranVO.setInitiatorUserName("");
		}else {
			tranVO.setInitiatorUserId(initiator.getId());
			tranVO.setInitiatorUserName(initiator.getUserName());
		}
		User receiver = tran.getReceiverUser();
		if(receiver == null) {
			tranVO.setReceiverUserId(0);
			tranVO.setReceiverUserName("");
		}else {
			tranVO.setReceiverUserId(receiver.getId());
			tranVO.setReceiverUserName(receiver.getUserName());
		}
		
		Painting painting = tran.getPainting();
		if(painting == null) {
			tranVO.setPaintingHash("");
			tranVO.setPaintingId(0);
			tranVO.setPaintingName("");
		}else {
			tranVO.setPaintingHash(painting.getPaintHash());
			tranVO.setPaintingId(painting.getId());
			tranVO.setPaintingName(painting.getPaintName());
		}
		tranVO.setTranAmount(tran.getTranAmount());
		tranVO.setRegBlock(tran.getRegBlock());
		tranVO.setTranHash(tran.getTranHash());
		tranVO.setTranId(tran.getTranId());
		return tranVO;
	}
	
	private Transactions convertVO(TransactionsVO transactionsVO){
		Transactions tran = new Transactions();
		tran.setGenTime(transactionsVO.getGenTime());
		tran.setTranAmount(transactionsVO.getTranAmount());
		Optional<User> user = userService.findOne(transactionsVO.getInitiatorUserId());
		tran.setInitiatorUser(user.orElse(null));
		Optional<User> user2 = userService.findOne(transactionsVO.getReceiverUserId());
		tran.setReceiverUser(user2.orElse(null));
		Optional<Painting> painting = paintingService.findById(transactionsVO.getPaintingId());
		tran.setPainting(painting.orElse(null));
		return tran;
	}
	
	private List<TransactionsVO> convertPOJOList(List<Transactions> pojoList){
		List<TransactionsVO> tranVOList = new ArrayList<TransactionsVO>();
		for(Transactions tran:pojoList) {
			TransactionsVO tranVO = convertPOJO(tran);
			tranVOList.add(tranVO);
		}
		return tranVOList;
	}
}
