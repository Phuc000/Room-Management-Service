package com.cnpmnc.roms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomScheduleDto {
    private Long id;
    private Long roomId;
    private Long lecturerId;
    private Long subjectId;
    private LocalDate date;
    private int startSession;
    private int endSession;
}
