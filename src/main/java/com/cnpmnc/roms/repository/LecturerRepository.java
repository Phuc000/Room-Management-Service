package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    List<Lecturer> findByEmail(String email);
}
