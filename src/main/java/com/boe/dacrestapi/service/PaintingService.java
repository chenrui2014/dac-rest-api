package com.boe.dacrestapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boe.dacrestapi.model.Painting;

public interface PaintingService {

	List<Painting> findAll();
	Page<Painting> findAllPaging(Pageable pageable);
	Page<Painting> findByUserId(long userId,Pageable pageable);
}
