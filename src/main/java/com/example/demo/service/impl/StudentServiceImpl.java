package com.example.demo.service.impl;

import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.GeneralException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudentServiceImpl implements StudentService {
//    private final List<Student> students = new ArrayList<>();
    private final StudentRepository studentRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor2;

    @Override
    public Student addStudent(Student student) throws GeneralException, ValidationException {
        Callable<Student> callable = () -> {
            log.info("Thread for adding student have been started");
            if (student.getName() == null)
                throw new GeneralException("name is required", ErrorCode.NAME_IS_REQUIRED);
            if (student.getAge() < 18)
                throw new ValidationException("age must be greater than 18");

            Callable<Student> x = () -> studentRepository.save(student);
            return threadPoolTaskExecutor2.submit(x).get();
        };
        log.info("adding student thread created");

        try {
            Future<Student> future = threadPoolTaskExecutor.submit(callable);
            Student s = future.get();
            log.info("creating student finished");
            return s;
        } catch (InterruptedException | ExecutionException e) {
            log.error("creating student failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(Long id) throws ValidationException {
        throw new ValidationException("bla bla bla");
//        students.removeIf(s -> s.getId().equals(id));
    }

    @Override
    public Student findById(Long id) throws GeneralException {
        return studentRepository.findById(id).orElseThrow(() ->
                new GeneralException("student not found", ErrorCode.STUDENT_NOT_FOUND));
    }

    @Override
    public List<Student> findAll() {
        return null;
    }
}
