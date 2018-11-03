package com.example.alecu.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.alecu.application.models.Bike;
import com.example.alecu.application.repositories.BikeRepository;
import com.example.alecu.application.service.BikeService;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BikeController.class);
	
	@Autowired
	private BikeService bikeService;

	@GetMapping
	public List<Bike> list(){
		return bikeService.findAll();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void create(@RequestBody Bike bike) {
		bikeService.save(bike);
	}
	
	@GetMapping("/{id}")
	public Bike get(@PathVariable("id") long id) {
		return bikeService.getOne(id);
	}
	
}
