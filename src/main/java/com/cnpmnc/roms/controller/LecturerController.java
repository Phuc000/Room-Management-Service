package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.dto.LecturerDto;
import com.cnpmnc.roms.service.LecturerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

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
}
