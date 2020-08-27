package com.example.demo.controller;

import com.example.demo.domain.City;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://basic-app-react.netlify.app"})
@RequestMapping("/api")
@RestController
public class CityContoller {

    @Autowired
    CityService cityService;

    @GetMapping("/cities")
    public List<City> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        City city;
        try {
            city = cityService.findById(id);
            if (city == null)
                return new ResponseEntity<>(
                        Problem.valueOf(Status.NOT_FOUND, "El id ".concat(id.toString()).concat(" no existe.")),
                        HttpStatus.NOT_FOUND);
        } catch (
                DataAccessException ex) {
            return new ResponseEntity<>(
                    Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Ocurrio un problema en el servidor: ".concat(ex.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
}
