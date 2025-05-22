package com.cnpmnc.roms.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class DeleteRequestDto {
    private LocalDate date;
    private String startSession;
}
