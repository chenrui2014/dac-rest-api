package com.boe.dacrestapi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.boe.dacrestapi.model.Income;
import com.boe.dacrestapi.repository.IncomeRepository;
import com.boe.dacrestapi.service.IncomeService;

@Service
@Transactional
public class IncomeServiceImpl implements IncomeService {
	@Autowired
	private IncomeRepository incomeRepository;
	@Override
	public List<Income> findAll() {
		Iterator<Income> incomeIt = incomeRepository.findAll().iterator();
		List<Income> incomeList = new ArrayList<Income>();
		while (incomeIt.hasNext()) {
			Income income = incomeIt.next();
			incomeList.add(income);
		}
		return incomeList;
	}

	@Override
	public List<Income> findAll(Sort sort) {
		Iterator<Income> incomeIt = incomeRepository.findAll(sort).iterator();
		List<Income> incomeList = new ArrayList<Income>();
		while (incomeIt.hasNext()) {
			Income income = incomeIt.next();
			incomeList.add(income);
		}
		return incomeList;
	}

	@Override
	public Page<Income> findAllPaging(Pageable pageable) {
		return incomeRepository.findAll(pageable);
	}

	@Override
	public Page<Income> findByUserId(long userId, Pageable pageable) {
		return incomeRepository.findByUser_IdOrderByTranTimeDesc(userId, pageable);
	}

	@Override
	public List<Income> findByUserIdNoPage(long userId) {
		
		return incomeRepository.findByUser_IdOrderByTranTimeDesc(userId);
	}

	@Override
	public Iterable<Income> saveAll(Iterable<Income> incomes) {
		return incomeRepository.saveAll(incomes);
	}

}
