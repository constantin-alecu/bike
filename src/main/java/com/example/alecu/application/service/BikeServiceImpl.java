package com.example.alecu.application.service;

import com.example.alecu.application.controller.BikeController;
import com.example.alecu.application.models.Bike;
import com.example.alecu.application.queue.OrderTransactionSender;
import com.example.alecu.application.repositories.BikeRepository;
import com.example.alecu.application.repositories.elasticsearch.ElasticSearchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.alecu.application.service.BikeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeServiceImpl implements BikeService{

	private static final Logger LOGGER = LoggerFactory.getLogger(BikeServiceImpl.class);

	@Autowired
	private BikeRepository bikeRepository;

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;

	@Autowired
	private OrderTransactionSender orderTransactionSender;

	@Override
	public List<Bike> findAll() {
		return bikeRepository.findAll();
	}

	@Override
	public void save(Bike bike) {

		//sending message to queue
		orderTransactionSender.sendTransaction(bike);
		//saving queue to DB
		Bike saveBike = bikeRepository.save(bike);
		ObjectMapper mapper = new ObjectMapper();
		String bikeDocument = null;
		try {
			bikeDocument = mapper.writeValueAsString(saveBike);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//sending bike to elastic search
		if(bikeDocument != null){
			elasticSearchRepository.index(bikeDocument, "bikes", "bikes", saveBike.getId() + "");
		}
	}

	@Override
	public Bike getOne(long id) {
		return bikeRepository.getOne(id);
	}

	@Override
	public List<Bike> searchBikes(String bikeName) {
		LOGGER.info("searching bikes ... [" + bikeName + "]");
		List<String> bikeIds = elasticSearchRepository.search(bikeName, "model", "bikes");
		LOGGER.info("found " + bikeIds.size() + " bikes in ElasticSearch");

		if (!bikeIds.isEmpty()) {
			List<Long> ids = new ArrayList<>();
			bikeIds.forEach(id -> ids.add(Long.parseLong(id)));
			return bikeRepository.findAllById(ids);
		}
		return new ArrayList<>();

	}

	@Override
	public List<Bike> searchBikesOnlyInDB(String bikeName) {
		LOGGER.info("searching bikes ... [" + bikeName + "]");
		return bikeRepository.findByModelContaining(bikeName);


	}
}
