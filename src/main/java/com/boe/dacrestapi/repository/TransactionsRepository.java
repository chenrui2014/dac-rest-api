package com.boe.dacrestapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.Transactions;

@RepositoryRestResource(collectionResourceRel="transactions",path="tran")
public interface TransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {

	Page<Transactions> findByInitiatorUser_IdOrderByGenTimeDesc(@Param("initiator") String initiator, 
			Pageable pageable);
	Page<Transactions> findByReceiverUser_IdOrderByGenTimeDesc(@Param("receiver") String receiver,
			Pageable pageable);
	Page<Transactions> findByPainting_IdOrderByGenTimeDesc(@Param("paintingId") String paintingId, 
			Pageable pageable);
	Page<Transactions> findByInitiatorUser_IdOrReceiverUser_IdOrderByGenTimeDesc(@Param("initiator") String initiator, 
			@Param("receiver") String receiver,Pageable pageable);
}
