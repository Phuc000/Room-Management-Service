package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.LecturerCreationDto;
import com.cnpmnc.roms.dto.LecturerDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Role;

public class LecturerMapper {

    public static LecturerDto mapToLecturerDto(Lecturer lecturer) {
        return new LecturerDto(
                lecturer.getId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail(),
                lecturer.getDepartment());
    }

    public static Lecturer mapToLecturer(LecturerCreationDto lecturerDto) {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(lecturerDto.getId());
        lecturer.setFirstName(lecturerDto.getFirstName());
        lecturer.setLastName(lecturerDto.getLastName());
        lecturer.setEmail(lecturerDto.getEmail());
        lecturer.setPassword(lecturerDto.getPassword());
        lecturer.setDepartment(lecturerDto.getDepartment());
        lecturer.setRole(Role.LECTURER);
        return lecturer;

    }
}