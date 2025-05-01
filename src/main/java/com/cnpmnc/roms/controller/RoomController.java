package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.RoomDto;
import com.cnpmnc.roms.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        RoomDto newRoomDto = roomService.createRoom(roomDto);
        return new ResponseEntity<>(newRoomDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        RoomDto roomDto = roomService.getRoomById(id);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long id, @RequestBody RoomDto updatedRoomDto) {
        RoomDto roomDto = roomService.updateRoom(id, updatedRoomDto);
        return ResponseEntity.ok(roomDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>("Room deleted successfully!", HttpStatus.OK);
    }
}
