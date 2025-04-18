package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    BaseUser findByEmail(String email);
    boolean existsByEmail(String email);
}
