package com.boe.dacrestapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.Income;
@RepositoryRestResource(collectionResourceRel="income",path="income")
public interface IncomeRepository extends PagingAndSortingRepository<Income, Long> {

	List<Income> findByUser_IdOrderByTranTimeDesc(@Param("userId")long userId);
	Page<Income> findByUser_IdOrderByTranTimeDesc(@Param("userId")long userId,Pageable pageable);
}
