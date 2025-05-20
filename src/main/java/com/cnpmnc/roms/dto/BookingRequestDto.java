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
    private String subjectCode;
    private int startSession;
    private int endSession;
}
