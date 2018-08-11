package com.boe.dacrestapi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.repository.UserRepository;
import com.boe.dacrestapi.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		Iterator<User> userIt = userRepository.findAll().iterator();
		List<User> userList = new ArrayList<User>();
		while (userIt.hasNext()) {
			User user = userIt.next();
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Optional<User> findOne(long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User findByUserName(String userName) {
		
		return userRepository.findByUserName(userName);
	}

	@Override
	public void delete(long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public User verifyUser(String userName, String password) {
		return userRepository.findByUserNameAndPassword(userName, password);
	}

}
