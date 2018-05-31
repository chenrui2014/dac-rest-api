package com.boe.dacrestapi.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.ChainBlock;

@RepositoryRestResource(collectionResourceRel="chainBlock",path="cb")
public interface ChainBlockRepository extends PagingAndSortingRepository<ChainBlock, Long> {

	/**
	 * 获得某一节点的所有区块
	 * @description：功能描述 
	 * 
	 * @param nodeId
	 * @return
	 * @see： 需要参见的其它内容
	 */
	List<ChainBlock> findByNodeId(@Param("nodeId") int nodeId);
}
