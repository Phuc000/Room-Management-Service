package com.cnpmnc.roms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequestDto 
{
    private LocalDate date;
    private String name;
    private String building;
    private String campus;
    private Long subjectId;
    private int startSession;
    private int endSession;
}
