package com.boe.dacrestapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.ChainBlock;

@RepositoryRestResource(collectionResourceRel="chainBlock",path="cb")
public interface ChainBlockRepository extends PagingAndSortingRepository<ChainBlock, Long> {

}
