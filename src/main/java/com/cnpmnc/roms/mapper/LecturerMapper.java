package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.LecturerDto;
import com.cnpmnc.roms.entity.Lecturer;

public class LecturerMapper {

    public static LecturerDto mapToLecturerDto(Lecturer lecturer) {
        return new LecturerDto(
                lecturer.getId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail(),
                lecturer.getDepartment());
    }

    public static Lecturer mapToLecturer(LecturerDto lecturerDto) {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(lecturerDto.getId());
        lecturer.setFirstName(lecturerDto.getFirstName());
        lecturer.setLastName(lecturerDto.getLastName());
        lecturer.setEmail(lecturerDto.getEmail());
        lecturer.setDepartment(lecturerDto.getDepartment());
        return lecturer;

    }
}