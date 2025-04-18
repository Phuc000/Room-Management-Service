package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.StudentCreationDto;
import com.cnpmnc.roms.entity.Role;
import com.cnpmnc.roms.entity.Student;

public class StudentMapper {

    public static Student mapToStudent(StudentCreationDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setPassword(studentDto.getPassword());
        student.setMajor(studentDto.getMajor());
        student.setRole(Role.STUDENT);
        return student;
    }
}
