package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.dto.ScheduleFilterDto;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/roomschedules")
public class RoomScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;

    @GetMapping
    public ResponseEntity<List<RoomScheduleDto>> getAllRoomSchedules() {
        List<RoomScheduleDto> roomScheduleDtos = roomScheduleService.getAllRoomSchedules();
        return ResponseEntity.ok(roomScheduleDtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<RoomScheduleDto> createRoomSchedule(@RequestBody RoomScheduleDto roomScheduleDto) {
        RoomScheduleDto newRoomScheduleDto = roomScheduleService.createRoomSchedule(roomScheduleDto);
        return new ResponseEntity<>(newRoomScheduleDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomScheduleDto> getRoomScheduleById(@PathVariable Long id) {
        RoomScheduleDto roomScheduleDto = roomScheduleService.getRoomScheduleById(id);
        return ResponseEntity.ok(roomScheduleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<String> deleteRoomScheduleById(@PathVariable Long id) {
        roomScheduleService.deleteRoomSchedule(id);
        return new ResponseEntity<>("Room schedule deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<RoomScheduleDto> updateRoomScheduleById(@PathVariable Long id, @RequestBody RoomScheduleDto updatedRoomScheduleDto) {
        RoomScheduleDto roomScheduleDto = roomScheduleService.updateRoomSchedule(id, updatedRoomScheduleDto);
        return ResponseEntity.ok(roomScheduleDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RoomScheduleDto>> getSchedulesByFilters(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) String building,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        ScheduleFilterDto filterDto = new ScheduleFilterDto(roomId, building, startDate, endDate, floor);
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByFilters(filterDto);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/day")
    public ResponseEntity<List<RoomScheduleDto>> getSchedulesForDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesForDay(date);
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/week")
    public ResponseEntity<List<RoomScheduleDto>> getSchedulesForWeek(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek) {
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesForWeek(startOfWeek);
        return ResponseEntity.ok(schedules);
    }
}
