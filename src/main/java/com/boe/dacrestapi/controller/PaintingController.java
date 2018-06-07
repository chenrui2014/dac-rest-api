package com.boe.dacrestapi.controller;

import static org.assertj.core.api.Assertions.useRepresentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.PaintingService;
import com.boe.dacrestapi.service.UserService;
import com.boe.dacrestapi.utils.MessageDigestUtils;
import com.boe.dacrestapi.vo.PaintingVO;

@RestController
public class PaintingController {

	@Autowired
	private PaintingService paintingService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/paintings", method=RequestMethod.GET)
	public List<PaintingVO> getAllPaintings() {
		List<Painting> paintingList = paintingService.findAll();
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
	
	@RequestMapping(value="/buyPainting", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> buyPainting(@RequestBody PaintingVO paintingVO) {
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
		painting2.setTransactionId(MessageDigestUtils.sha1(md5Str));
		
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
		painting2.setTransactionId(MessageDigestUtils.sha1(md5Str));
		
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
	public List<PaintingVO> getUserPaintingNoPage(@RequestParam(value = "userId")Long userId, @RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		
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
		paintingVO.setRegTime(painting.getRegTime());
		paintingVO.setTransactionId(painting.getTransactionId());
		paintingVO.setPaintDes(painting.getPaintDes());
		paintingVO.setPaintUrl(painting.getPaintUrl());
		paintingVO.setType(painting.getType());
		paintingVO.setStatus(painting.getStatus());
		
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
		painting.setRegTime(paintingVO.getRegTime());
		painting.setTransactionId(paintingVO.getTransactionId());
		painting.setPaintDes(paintingVO.getPaintDes());
		painting.setPaintUrl(paintingVO.getPaintUrl());
		painting.setType(paintingVO.getType());
		
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
