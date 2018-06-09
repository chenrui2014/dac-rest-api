package com.boe.dacrestapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.boe.dacrestapi.model.Income;

public interface IncomeService {

	List<Income> findAll();
	List<Income> findAll(Sort sort);
	Page<Income> findAllPaging(Pageable pageable);
	Page<Income> findByUserId(long userId,Pageable pageable);
	List<Income> findByUserIdNoPage(long userId);
}
