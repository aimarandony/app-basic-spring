package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/students")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student == null)
            return new ResponseEntity<>("Id ".concat(id.toString()).concat(" no encontrado."), HttpStatus.FOUND);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<?> create(@RequestBody Student student){
        Student createStudent = studentService.create(student);
        if (createStudent == null)
            return new ResponseEntity<>("Ocurrio un problema al crear el estudiante: ".concat(student.getName()), HttpStatus.FOUND);

        return new ResponseEntity<>(createStudent, HttpStatus.OK);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable Long id){
        Student updateStudent = studentService.update(student, id);
        if (updateStudent == null)
            return new ResponseEntity<>("Ocurrio un problema al actualizar el estudiante: ".concat(student.getName()), HttpStatus.FOUND);

        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @GetMapping("/students/pdf")
    public ResponseEntity<byte[]> getListStudentsPdf() {
        byte[] contents = studentService.getListStudentsPdf().toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "students.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping("/students/excel")
    public ResponseEntity<InputStreamResource> getListStudentsExcel() {
        ByteArrayInputStream in = studentService.getListStudentsExcel();
        HttpHeaders headers = new HttpHeaders();
        String filename = "students.xlsx";
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}
