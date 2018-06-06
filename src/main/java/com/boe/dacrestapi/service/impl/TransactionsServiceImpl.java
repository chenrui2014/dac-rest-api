package com.boe.dacrestapi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.repository.TransactionsRepository;
import com.boe.dacrestapi.service.TransactionsService;

@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	@Override
	public List<Transactions> findAll() {
		Iterator<Transactions> tranIt = transactionsRepository.findAll().iterator();
		List<Transactions> tranList = new ArrayList<Transactions>();
		while (tranIt.hasNext()) {
			Transactions painting = tranIt.next();
			tranList.add(painting);
		}
		return tranList;
	}
	@Override
	public Page<Transactions> findAll(Pageable pageable) {
		return transactionsRepository.findAll(pageable);
	}
	@Override
	public Page<Transactions> findTransactionsByUser(Long userId, Pageable pageable) {
		return transactionsRepository.findByInitiatorUser_IdOrReceiverUser_IdOrderByGenTimeDesc(userId, userId, pageable);
	}

}
