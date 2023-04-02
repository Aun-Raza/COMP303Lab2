package com.comp303.lab2.repository;

import com.comp303.lab2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUsernameAndPassword(String username, String password);
}
