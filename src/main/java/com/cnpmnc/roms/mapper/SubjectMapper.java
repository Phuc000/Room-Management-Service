package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.dto.SubjectDto;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.entity.Subject;

public class SubjectMapper {
    public static SubjectDto mapToSubjectDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode());
    }
}
