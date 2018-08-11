package com.boe.dacrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boe.dacrestapi.model.Transactions;

public interface TransactionsService {

	List<Transactions> findAll();
	Page<Transactions> findAll(Pageable pageable);
	Page<Transactions> findTransactionsByUser(Long userId, Pageable pageable);
	Optional<Transactions> findById(long id);
	Transactions save(Transactions transactions);
}
