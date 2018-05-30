package com.boe.dacrestapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.Painting;

@RepositoryRestResource(collectionResourceRel="painting",path="painting")
public interface PaintingRepository extends PagingAndSortingRepository<Painting, Long> {

}
