package com.example.alecu.application.service;

import com.example.alecu.application.models.Bike;
import com.example.alecu.application.repositories.BikeRepository;
import com.example.alecu.application.repositories.elasticsearch.ElasticSearchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.alecu.application.service.BikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService{

	@Autowired
	private BikeRepository bikeRepository;

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;

	@Override
	public List<Bike> findAll() {
		return bikeRepository.findAll();
	}

	@Override
	public void save(Bike bike) {
		Bike saveBike = bikeRepository.save(bike);
		ObjectMapper mapper = new ObjectMapper();
		String bikeDocument = null;
		try {
			bikeDocument = mapper.writeValueAsString(saveBike);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if(bikeDocument != null){
			elasticSearchRepository.index(bikeDocument, "bikes", "bikes", saveBike.getId() + "");
		}
	}

	@Override
	public Bike getOne(long id) {
		return bikeRepository.getOne(id);
	}
}
