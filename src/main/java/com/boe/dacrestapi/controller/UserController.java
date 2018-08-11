package com.boe.dacrestapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.UserService;
import com.boe.dacrestapi.vo.UserVO;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/users", method=RequestMethod.GET)
	public List<User> getUser() {
		List<User> userList = userService.findAll();
		for (User user:userList) {
			user.setPaintings(null);
			user.setInitiatingTran(null);
			user.setReceiveTran(null);
		}
		return userService.findAll();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "userName")String userName, @RequestParam(value = "password")String password, HttpSession session){
		User user = userService.verifyUser(userName, password);
		Map<String, Object> body = new HashMap<>();
		if(user == null) {
			body.put("data", null);
			body.put("message", "登陆失败！");
			body.put("success", "false");
		}else {
			session.setAttribute("user", convertPOJO(user));
			body.put("data", convertPOJO(user));
			body.put("message", "登陆成功！");
			body.put("success", "true");
		}
		
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	private UserVO convertPOJO(User user) {
		UserVO userVO = new UserVO();
		userVO.setEmail(user.getEmail());
		userVO.setHashString(user.getHashString());
		userVO.setId(user.getId());
		userVO.setNickName(user.getNickName());
		userVO.setPassword(user.getPassword());
		userVO.setUserName(user.getUserName());
		userVO.setRegTime(user.getRegTime());
		return userVO;
	}
	
	private List<UserVO> convertPOJOList(List<User> userList){
		List<UserVO> userVOs = new ArrayList<UserVO>();
		for(User user:userList) {
			UserVO userVO = convertPOJO(user);
			userVOs.add(userVO);
		}
		return userVOs;
	}
}
