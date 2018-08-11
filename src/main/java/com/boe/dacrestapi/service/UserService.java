package com.boe.dacrestapi.service;

import java.util.List;
import java.util.Optional;

import com.boe.dacrestapi.model.User;

public interface UserService {

	public List<User> findAll();
	public Optional<User> findOne(long userId);
	public User findByUserName(String userName);
	public void delete(long userId);
	public User verifyUser(String userName,String password);
}
