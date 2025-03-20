package com.cnpmnc.roms.controller;


import com.cnpmnc.roms.dto.AuthRequest;
import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.mapper.LecturerMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public String authenticateLecturer(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/lecturer/signup")
    public String registerLecturer(@RequestBody LecturerCreationDto lecturerCreationDto) {
        if (!lecturerRepository.findByEmail(lecturerCreationDto.getEmail()).isEmpty()) {
            throw new RuntimeException("Email already exists");
        }
        Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerCreationDto);
        lecturer.setPassword(encoder.encode(lecturer.getPassword()));
        lecturerRepository.save(lecturer);
        return "Lecturer registered successfully";
    }
}
