package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.LecturerDto;

import java.util.List;

public interface LecturerService {
    LecturerDto createLecturer(LecturerDto lecturerDto);

    List<LecturerDto> getAllLecturers();

    LecturerDto getLecturerById(Long id);

    LecturerDto updateLecturer(Long id, LecturerDto updatedLecturerDto);

    void deleteLecturer(Long id);
}
