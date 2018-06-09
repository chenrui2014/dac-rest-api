package com.boe.dacrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.boe.dacrestapi.model.Painting;

public interface PaintingService {

	List<Painting> findAll();
	List<Painting> findAll(Sort sort);
	Page<Painting> findAllPaging(Pageable pageable);
	Page<Painting> findByUserId(long userId,Pageable pageable);
	List<Painting> findByUserIdNoPage(long userId);
	Optional<Painting> findById(long id);
	Painting save(Painting painting);
}
