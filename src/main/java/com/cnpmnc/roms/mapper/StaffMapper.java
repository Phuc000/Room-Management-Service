package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.StaffCreationDto;
import com.cnpmnc.roms.entity.Role;
import com.cnpmnc.roms.entity.Staff;

public class StaffMapper {

    public static Staff mapToStaff(StaffCreationDto staffCreationDto) {
        if (staffCreationDto == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(staffCreationDto.getId());
        staff.setFirstName(staffCreationDto.getFirstName());
        staff.setLastName(staffCreationDto.getLastName());
        staff.setEmail(staffCreationDto.getEmail());
        staff.setPassword(staffCreationDto.getPassword());
        staff.setPosition(staffCreationDto.getPosition());
        staff.setRole(Role.STAFF);
        return staff;
    }
}
