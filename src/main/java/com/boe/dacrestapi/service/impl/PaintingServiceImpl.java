package com.boe.dacrestapi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.repository.PaintingRepository;
import com.boe.dacrestapi.service.PaintingService;

@Service
@Transactional
public class PaintingServiceImpl implements PaintingService {

	@Autowired
	private PaintingRepository paintingRepository;
	@Override
	public List<Painting> findAll() {
		Iterator<Painting> paintingIt = paintingRepository.findAll().iterator();
		List<Painting> paintingList = new ArrayList<Painting>();
		while (paintingIt.hasNext()) {
			Painting painting = paintingIt.next();
			paintingList.add(painting);
		}
		return paintingList;
	}
	@Override
	public Page<Painting> findAllPaging(Pageable pageable) {
	
		return paintingRepository.findAll(pageable);
	}
	@Override
	public Page<Painting> findByUserId(long userId, Pageable pageable) {
		return paintingRepository.findByUser_IdOrderByRegTimeDesc(userId, pageable);
	}
	@Override
	public Painting save(Painting painting) {

		return paintingRepository.save(painting);
	}
	@Override
	public Optional<Painting> findById(long id) {
		return paintingRepository.findById(id);
	}
	@Override
	public List<Painting> findByUserIdNoPage(long userId) {
		return paintingRepository.findByUser_IdOrderByRegTimeDesc(userId);
	}
	@Override
	public List<Painting> findAll(Sort sort) {
		Iterable<Painting> paintingIt = paintingRepository.findAll(sort);
		Iterator<Painting> paintIt = paintingIt.iterator();
		List<Painting> paintings = new ArrayList<Painting>();
		while(paintIt.hasNext()) {
			Painting painting = paintIt.next();
			paintings.add(painting);
		}
		return paintings;
	}

}
