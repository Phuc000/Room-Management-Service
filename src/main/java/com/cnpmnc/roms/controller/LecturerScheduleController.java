package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.BaseUser;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lecturer/schedules")
@PreAuthorize("hasRole('ROLE_LECTURER')")
public class LecturerScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;

    // Get current lecturer's schedules
    @GetMapping("/my")
    public ResponseEntity<List<RoomScheduleDto>> getMySchedules() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseUser currentUser = (BaseUser) authentication.getPrincipal();
        Long lecturerId = currentUser.getId();
        
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByLecturerId(lecturerId);
        return ResponseEntity.ok(schedules);
    }
    
    // Get current lecturer's schedules by date range
    @GetMapping("/my/date-range")
    public ResponseEntity<List<RoomScheduleDto>> getMySchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseUser currentUser = (BaseUser) authentication.getPrincipal();
        Long lecturerId = currentUser.getId();
        
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByLecturerIdAndDateRange(lecturerId, startDate, endDate);
        return ResponseEntity.ok(schedules);
    }
    
    // Admin-only endpoint to get schedules for any lecturer
    @GetMapping("/lecturer/{lecturerId}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<List<RoomScheduleDto>> getLecturerSchedules(@PathVariable Long lecturerId) {
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByLecturerId(lecturerId);
        return ResponseEntity.ok(schedules);
    }
}