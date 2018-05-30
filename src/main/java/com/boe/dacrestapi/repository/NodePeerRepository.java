package com.boe.dacrestapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.NodePeer;

@RepositoryRestResource(collectionResourceRel="nodePeer",path="node")
public interface NodePeerRepository extends PagingAndSortingRepository<NodePeer, Long> {

}
