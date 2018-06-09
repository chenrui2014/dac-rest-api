package com.boe.dacrestapi.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.User;

@RepositoryRestResource(collectionResourceRel="user",path="user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserName(@Param("userName") String userName);
	User findByHashString(@Param("hash") String hashString);
	List<User> findByUserNameOrEmail(@Param("userName") String userName,@Param("email") String email);
	User findByUserNameAndPassword(@Param("userName") String userName,@Param("password") String password);
}
