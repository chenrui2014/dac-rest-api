package com.boe.dacrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.UserService;

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

}
