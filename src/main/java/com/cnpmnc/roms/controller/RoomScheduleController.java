package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.service.RoomScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
