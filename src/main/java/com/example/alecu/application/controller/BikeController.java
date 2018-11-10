package com.example.alecu.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
	public List<Bike> searchBikes(@RequestParam(value="searchText", required=false) String bikeName,
								  @RequestParam(value="database", required = false) Boolean databaseSearch){
		if(bikeName == null || bikeName.isEmpty()) {
			return bikeService.findAll();
		}if(databaseSearch != null && databaseSearch){
			return bikeService.searchBikesOnlyInDB(bikeName);
		}else{
			return bikeService.searchBikes(bikeName);
		}
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
