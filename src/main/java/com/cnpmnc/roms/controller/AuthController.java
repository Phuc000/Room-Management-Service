package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.AuthRequest;
import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.dto.StudentCreationDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Student;
import com.cnpmnc.roms.mapper.LecturerMapper;
import com.cnpmnc.roms.mapper.StudentMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.repository.StudentRepository;
import com.cnpmnc.roms.repository.UserRepository;
import com.cnpmnc.roms.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateLecturer(
            @RequestBody AuthRequest authRequest,
            HttpServletResponse response
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("CredentialCookie", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);
        return ResponseEntity.ok("Login successful!");
    }

    @PostMapping("/lecturer/signup")
    public String registerLecturer(@RequestBody LecturerCreationDto lecturerCreationDto) {
        if (userRepository.existsByEmail(lecturerCreationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerCreationDto);
        lecturer.setPassword(encoder.encode(lecturer.getPassword()));
        lecturerRepository.save(lecturer);
        return "Lecturer registered successfully";
    }

    @PostMapping("/student/signup")
    public String registerStudent(@RequestBody StudentCreationDto studentCreationDto) {
        if (userRepository.existsByEmail(studentCreationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Student student = StudentMapper.mapToStudent(studentCreationDto);
        student.setPassword(encoder.encode(student.getPassword()));
        studentRepository.save(student);
        return "Student registered successfully";
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("CredentialCookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Delete the cookie by setting maxAge to 0

        response.addCookie(cookie);
        return ResponseEntity.ok("Signout successful!");
    }
}
