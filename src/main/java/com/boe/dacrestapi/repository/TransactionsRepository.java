package com.boe.dacrestapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.Transactions;

@RepositoryRestResource(collectionResourceRel="transactions",path="tran")
public interface TransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {

}
