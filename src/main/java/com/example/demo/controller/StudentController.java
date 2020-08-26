package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

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
        Student student;
        try {
            student = studentService.findById(id);
            if (student == null) {
                return new ResponseEntity<>(
                        Problem.valueOf(Status.BAD_REQUEST, "El id ".concat(id.toString()).concat(" no existe.")),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(
                    Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Ocurrio un problema en el servidor: ".concat(ex.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<?> create(@RequestBody Student student) {
        Student createStudent;
        try {
            createStudent = studentService.create(student);
            if (createStudent == null)
                return new ResponseEntity<>(
                        Problem.valueOf(Status.BAD_REQUEST, "Ocurrio un problema al crear el estudiante:".concat(student.getName())),
                        HttpStatus.BAD_REQUEST);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(
                    Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Ocurrio un problema en el servidor: ".concat(ex.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createStudent, HttpStatus.OK);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable Long id) {
        Student updateStudent;
        try {
            updateStudent = studentService.update(student, id);
            if (updateStudent == null)
                return new ResponseEntity<>(
                        Problem.valueOf(Status.FOUND, "Ocurrio un problema al actualizar el estudiante:".concat(student.getName())),
                        HttpStatus.FOUND);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(
                    Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Ocurrio un problema en el servidor: ".concat(ex.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
