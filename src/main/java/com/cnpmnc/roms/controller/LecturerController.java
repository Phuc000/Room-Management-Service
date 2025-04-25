package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.dto.LecturerDto;
import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.service.LecturerService;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {
    private final LecturerService lecturerService;
    private final RoomScheduleService roomScheduleService;
    
    public LecturerController(LecturerService lecturerService, RoomScheduleService roomScheduleService) {
        this.lecturerService = lecturerService;
        this.roomScheduleService = roomScheduleService;
    }
    
    @PostMapping
    public ResponseEntity<LecturerDto> createLecturer(@RequestBody LecturerCreationDto lecturerDto) {
        LecturerDto createdLecturer = lecturerService.createLecturer(lecturerDto);
        return new ResponseEntity<>(createdLecturer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LecturerDto>> getAllLecturers() {
        return new ResponseEntity<>(lecturerService.getAllLecturers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturerDto> getLecturerById(@PathVariable("id") Long id) {
        LecturerDto lecturerDto = lecturerService.getLecturerById(id);
        return ResponseEntity.ok(lecturerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerDto> updateLecturer(@PathVariable("id") Long id,
                                                      @RequestBody LecturerDto updatedLecturerDto) {
        LecturerDto updatedLecturer = lecturerService.updateLecturer(id, updatedLecturerDto);
        return ResponseEntity.ok(updatedLecturer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable("id") Long id) {
        lecturerService.deleteLecturer(id);
        return ResponseEntity.ok("Lecturer deleted successfully!");
    }

    @GetMapping("/schedules/{lecturerId}")
    public ResponseEntity<List<RoomScheduleDto>> getLecturerSchedules(@PathVariable Long lecturerId) {
        List<RoomScheduleDto> schedules = roomScheduleService.getLecturerSchedules(lecturerId);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/my-schedules")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<RoomScheduleDto>> getMySchedules() {
        // Get currently authenticated lecturer
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        
        // Get lecturer by email
        List<com.cnpmnc.roms.entity.Lecturer> lecturerList = 
            ((com.cnpmnc.roms.repository.LecturerRepository)lecturerService).findByEmail(currentUserEmail);
        
        if (lecturerList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Long lecturerId = lecturerList.get(0).getId();
        List<RoomScheduleDto> schedules = roomScheduleService.getLecturerSchedules(lecturerId);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/my-schedules/filter")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<RoomScheduleDto>> getMySchedulesFiltered(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        
        List<com.cnpmnc.roms.entity.Lecturer> lecturerList = 
            ((com.cnpmnc.roms.repository.LecturerRepository)lecturerService).findByEmail(currentUserEmail);
        
        if (lecturerList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Long lecturerId = lecturerList.get(0).getId();
        
        // Get all lecturer schedules first
        List<RoomScheduleDto> allSchedules = roomScheduleService.getLecturerSchedules(lecturerId);
        
        // Filter by date range if provided
        if (startDate != null && endDate != null) {
            allSchedules = allSchedules.stream()
                .filter(schedule -> 
                    !schedule.getEndDate().isBefore(startDate) && 
                    !schedule.getStartDate().isAfter(endDate))
                .toList();
        }
        
        return ResponseEntity.ok(allSchedules);
    }
}
