package com.boe.dacrestapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boe.dacrestapi.model.Painting;

@RepositoryRestResource(collectionResourceRel="painting",path="painting")
public interface PaintingRepository extends PagingAndSortingRepository<Painting, Long> {

	/**
	 * 获得依赖的作品
	 * @description：功能描述 
	 * 
	 * @param denPaintHash
	 * @return
	 * @see： 需要参见的其它内容
	 */
	Painting findByDenPainting_Id(@Param("denPaintId") String denPaintId);
	/**
	 * 根据画作哈希获得作品
	 * @description：功能描述 
	 * 
	 * @param paintHash
	 * @return
	 * @see： 需要参见的其它内容
	 */
	Painting findByPaintHash(@Param("paintHash") String paintHash);
	/**
	 * 获得某一用户的作品
	 * @description：功能描述 
	 * 
	 * @param userId
	 * @return
	 * @see： 需要参见的其它内容
	 */
	Page<Painting> findByUser_IdOrderByRegTimeDesc(@Param("userId") long userId,Pageable pageable);
	/**
	 * 获得某一用户某一类型的作品
	 * @description：功能描述 
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @see： 需要参见的其它内容
	 */
	Page<Painting> findByUser_IdAndTypeOrderByRegTimeDesc(@Param("userId") long userId,
			@Param("type") int type,Pageable pageable);
	/**
	 * 获得某一作者的作品
	 * @description：功能描述 
	 * 
	 * @param author
	 * @return
	 * @see： 需要参见的其它内容
	 */
	Page<Painting> findByAuthorOrderByRegTimeDesc(@Param("author") String author,Pageable pageable);
	
	List<Painting> findByUser_IdOrderByRegTimeDesc(@Param("userId") long userId);
	
}
