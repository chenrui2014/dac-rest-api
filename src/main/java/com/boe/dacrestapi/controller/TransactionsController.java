package com.boe.dacrestapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.TransactionsService;
import com.boe.dacrestapi.vo.TransactionsVO;

@RestController
public class TransactionsController {

	@Autowired
	private TransactionsService tranService;
	
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
//		List<Transactions> tranList = new ArrayList<Transactions>();
//		while (tranIt.hasNext()) {
//			Transactions tran = tranIt.next();
//			tranList.add(tran);
//		}
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
	
	private List<TransactionsVO> convertPOJOList(List<Transactions> pojoList){
		List<TransactionsVO> tranVOList = new ArrayList<TransactionsVO>();
		for(Transactions tran:pojoList) {
			TransactionsVO tranVO = convertPOJO(tran);
			tranVOList.add(tranVO);
		}
		return tranVOList;
	}
}
