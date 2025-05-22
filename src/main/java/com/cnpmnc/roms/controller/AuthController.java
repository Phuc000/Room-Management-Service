package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.AuthRequest;
import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.dto.StaffCreationDto;
import com.cnpmnc.roms.dto.StudentCreationDto;
import com.cnpmnc.roms.entity.BaseUser;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Staff;
import com.cnpmnc.roms.entity.Student;
import com.cnpmnc.roms.mapper.LecturerMapper;
import com.cnpmnc.roms.mapper.StaffMapper;
import com.cnpmnc.roms.mapper.StudentMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.repository.StaffRepository;
import com.cnpmnc.roms.repository.StudentRepository;
import com.cnpmnc.roms.repository.UserRepository;
import com.cnpmnc.roms.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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
    StaffRepository staffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/user")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal BaseUser user) {
        if (user != null) {
            // Direct access to your user object
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getEmail());
            userInfo.put("password", "*****"); // Do not expose password
            userInfo.put("displayname", user.getFirstName() + " " + user.getLastName());
            userInfo.put("isGuest", false);
            
            // Check type for specific properties
            if (user instanceof Lecturer) {
                userInfo.put("role", "lecturer");
            } else if (user instanceof Student) {
                userInfo.put("role", "student");
            } else if (user instanceof Staff) {
                userInfo.put("role", "staff");
            }
            
            return ResponseEntity.ok(userInfo);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("CredentialCookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Delete the cookie by setting maxAge to 0

        response.setHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue() 
            + "; Max-Age=" + cookie.getMaxAge() 
            + "; Path=" + cookie.getPath() 
            + "; HttpOnly"
            + "; SameSite=None; Secure");
        
        // response.addCookie(cookie);
        
        return ResponseEntity.ok("Logout successful!");
    }

    @GetMapping("/login/guest")
    public ResponseEntity<?> authenticateGuest(HttpServletResponse response) {
        Map<String, Object> guestResponse = new HashMap<>();
        guestResponse.put("isGuest", true);
        
        // Return response with status 200
        return ResponseEntity.ok(guestResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateLecturer(
            @RequestBody AuthRequest authRequest,
            HttpServletResponse response
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
            
        // Create response object with user information
        Map<String, Object> userResponse = new HashMap<>();
        
        // Get user details from repository based on username
        if (userRepository.existsByEmail(username)) {
            BaseUser user = userRepository.findByEmail(username);
            
            Long userId;
            String displayName;
            String role;
            
            // Determine user type and extract appropriate information
            if (user instanceof Lecturer) {
                Lecturer lecturer = (Lecturer) user;
                userId = lecturer.getId();
                displayName = lecturer.getFirstName()+" "+lecturer.getLastName(); // Adjust property name if different
                role = "lecturer";
            } else if (user instanceof Student) {
                Student student = (Student) user;
                userId = student.getId();
                displayName = student.getFirstName()+" "+student.getLastName(); // Adjust property name if different
                role = "student";
            } else if (user instanceof Staff) {
                Staff staff = (Staff) user;
                userId = staff.getId();
                displayName = staff.getFirstName()+" "+staff.getLastName(); // Adjust property name if different
                role = "staff";
            } else {
                throw new RuntimeException("Unknown user type");
            }
            
            userResponse.put("id", userId);
            userResponse.put("username", username);
            userResponse.put("displayName", displayName);
            userResponse.put("role", role);
        } else {
            throw new RuntimeException("User not found");
        }
        
        // Generate JWT token and set cookie as before
        String token = jwtUtil.generateToken(userDetails);
        
        Cookie cookie = new Cookie("CredentialCookie", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.setHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue() 
            + "; Max-Age=" + cookie.getMaxAge() 
            + "; Path=" + cookie.getPath() 
            + "; HttpOnly"
            + "; SameSite=None; Secure");

        // response.addCookie(cookie);  
        
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/lecturer/signup")
    public ResponseEntity<Map<String, String>> registerLecturer(@RequestBody LecturerCreationDto lecturerCreationDto) {
        if (userRepository.existsByEmail(lecturerCreationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerCreationDto);
        lecturer.setPassword(encoder.encode(lecturer.getPassword()));
        lecturerRepository.save(lecturer);
        return ResponseEntity.ok(Map.of("message", "Lecturer registered successfully"));

    }

    @PostMapping("/student/signup")
    public ResponseEntity<Map<String, String>> registerStudent(@RequestBody StudentCreationDto studentCreationDto) {
        if (userRepository.existsByEmail(studentCreationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Student student = StudentMapper.mapToStudent(studentCreationDto);
        student.setPassword(encoder.encode(student.getPassword()));
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "Student registered successfully"));
    }

    @PostMapping("/staff/signup")
    public ResponseEntity<Map<String, String>> registerStaff(@RequestBody StaffCreationDto staffCreationDto) {
        if (userRepository.existsByEmail(staffCreationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Staff staff = StaffMapper.mapToStaff(staffCreationDto);
        staff.setPassword(encoder.encode(staff.getPassword()));
        staffRepository.save(staff);
        return ResponseEntity.ok(Map.of("message", "Staff registered successfully"));
    }
}
