package com.cnpmnc.roms.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private String lecturerName;
    private String subjectName;
    private String campus;
    private String building;
    private String roomNumber;

    public RoomScheduleDto() {
    }
    
    public RoomScheduleDto(Long id, Long roomId, Long lecturerId, Long subjectId, 
                          LocalDate date, int startSession, int endSession) {
        this.id = id;
        this.roomId = roomId;
        this.lecturerId = lecturerId;
        this.subjectId = subjectId;
        this.date = date;
        this.startSession = startSession;
        this.endSession = endSession;
    }
}
