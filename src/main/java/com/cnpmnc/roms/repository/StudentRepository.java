package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Student findByEmail(String email);
}
