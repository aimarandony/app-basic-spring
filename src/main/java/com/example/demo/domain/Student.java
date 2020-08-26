package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String sex;
    @ManyToOne
    private City city;

}
