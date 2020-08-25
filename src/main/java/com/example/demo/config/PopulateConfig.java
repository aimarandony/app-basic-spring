package com.example.demo.config;

import com.example.demo.domain.City;
import com.example.demo.domain.Student;
import com.example.demo.service.CityService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PopulateConfig {

    @Autowired
    CityService cityService;

    @Autowired
    StudentService studentService;

    @PostConstruct
    public void init(){
        databaseInit();
    }

    public void databaseInit(){
        City city1, city2, city3;
        Student student1, student2, student3, student4, student5;

        city1 = new City();
        city1.setName("Lima");
        cityService.create(city1);
        city2 = new City();
        city2.setName("Ica");
        cityService.create(city2);
        city3 = new City();
        city3.setName("Cuzco");
        cityService.create(city3);

        student1 = new Student();
        student1.setName("Aimar");
        student1.setLastName("Berrocal");
        student1.setSex("M");
        student1.setCity(city1);
        studentService.create(student1);
        student2 = new Student();
        student2.setName("Craig");
        student2.setLastName("Castro");
        student2.setSex("M");
        student2.setCity(city1);
        studentService.create(student2);
        student3 = new Student();
        student3.setName("Alex");
        student3.setLastName("Justiniano");
        student3.setSex("M");
        student3.setCity(city2);
        studentService.create(student3);
        student4 = new Student();
        student4.setName("Andrea");
        student4.setLastName("Galvez");
        student4.setSex("F");
        student4.setCity(city3);
        studentService.create(student4);
        student5 = new Student();
        student5.setName("Angela");
        student5.setLastName("Ramos");
        student5.setSex("F");
        student5.setCity(city3);
        studentService.create(student5);
    }
}
