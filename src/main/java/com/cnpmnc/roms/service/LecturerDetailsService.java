package com.cnpmnc.roms.service;

import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerDetailsService implements UserDetailsService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Lecturer> lecturer = lecturerRepository.findByEmail(email);
        if (lecturer.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Lecturer foundLecturer = lecturer.getFirst();
        return new org.springframework.security.core.userdetails.User(
                foundLecturer.getEmail(),
                foundLecturer.getPassword(),
                new ArrayList<>());
    }
}
