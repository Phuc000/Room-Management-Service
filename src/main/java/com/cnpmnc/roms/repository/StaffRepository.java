package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Staff;
import com.cnpmnc.roms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByEmail(String email);
    Student findByEmail(String email);
}
