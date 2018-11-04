package com.example.alecu.application.service;

import com.example.alecu.application.models.Bike;

import java.util.List;

public interface BikeService {


    List<Bike> findAll();

    void save(Bike bike);

    Bike getOne(long id);

    List<Bike> searchBikes(String bikeName);
}
