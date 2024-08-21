package com.example.demo.controller;

import com.example.demo.exceptions.GeneralException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/apis/student")
@RequiredArgsConstructor
@Log4j2
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) throws GeneralException, ValidationException {
        log.info("incoming request to add student: {}", student);
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.OK);
    }

    @GetMapping(value = "/{student-id}")
    public ResponseEntity<?> getStudent(@PathVariable("student-id") Long id) throws GeneralException {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{student-id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("student-id") Long id) throws ValidationException {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
