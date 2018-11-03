package com.example.alecu.application.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.alecu.application.models.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {

	

}
