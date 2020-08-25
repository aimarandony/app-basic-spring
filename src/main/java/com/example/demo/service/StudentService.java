package com.example.demo.service;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.view.excel.StudentExcel;
import com.example.demo.view.pdf.StudentPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public Student findById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public Student create(Student student){
        return studentRepository.save(student);
    }

    public Student update(Student student, Long id){
        Student updateStudent = findById(id);
        if (updateStudent == null) return null;
        updateStudent.setName(student.getName());
        updateStudent.setLastName(student.getLastName());
        updateStudent.setSex(student.getSex());
        updateStudent.setCity(student.getCity());
        return studentRepository.save(updateStudent);
    }

    public ByteArrayOutputStream getListStudentsPdf() {
        List<Student> students = findAll();
        StudentPdf studentPdf = new StudentPdf();
        return studentPdf.getListStudents(students);
    }

    public ByteArrayInputStream getListStudentsExcel() {
        List<Student> students = findAll();
        StudentExcel studentExcel = new StudentExcel();
        return studentExcel.getListStudents(students);
    }
}
