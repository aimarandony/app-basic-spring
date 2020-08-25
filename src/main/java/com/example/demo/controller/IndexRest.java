package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRest {

    @GetMapping("/")
    public ResponseEntity<String> init(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello World");
    }
}
