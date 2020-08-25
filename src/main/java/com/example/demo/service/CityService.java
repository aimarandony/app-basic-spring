package com.example.demo.service;

import com.example.demo.domain.City;
import com.example.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public List<City> findAll(){
        return cityRepository.findAll();
    }

    public City findById(Long id){
        return cityRepository.findById(id).orElse(null);
    }

    public City create(City city){
        return cityRepository.save(city);
    }
}
