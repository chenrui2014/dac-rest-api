package com.boe.dacrestapi.controller;

import static org.assertj.core.api.Assertions.useRepresentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.PaintingService;
import com.boe.dacrestapi.vo.PaintingVO;

@RestController
public class PaintingController {

	@Autowired
	private PaintingService paintingService;
	
	@RequestMapping(value="/paintings", method=RequestMethod.GET)
	public List<PaintingVO> getUser() {
		List<Painting> paintingList = paintingService.findAll();
		return convertPOJOList(paintingList);
	}
	@RequestMapping(value="/paintingsPerPage", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUserPerPage(@RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		Pageable pageable = new PageRequest(page,size);
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
		paintingVO.setRegTime(painting.getRegTime());
		paintingVO.setTransactionId(painting.getTransactionId());
		paintingVO.setType(painting.getType());
		paintingVO.setStatus(painting.getStatus());
		
		return paintingVO;
	}
	
	private List<PaintingVO> convertPOJOList(List<Painting> pojoList){
		
		List<PaintingVO> paintingVOs = new ArrayList<>();
		for(Painting painting:pojoList) {
			PaintingVO paintingVO = convertPOJO(painting);
			paintingVOs.add(paintingVO);
		}
		return paintingVOs;
	}
}
