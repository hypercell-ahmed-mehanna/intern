package com.example.demo.service;

import com.example.demo.exceptions.GeneralException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student) throws GeneralException, ValidationException;
    void deleteStudent(Long id) throws ValidationException;
    Student findById(Long id) throws GeneralException;
    List<Student> findAll();
}
