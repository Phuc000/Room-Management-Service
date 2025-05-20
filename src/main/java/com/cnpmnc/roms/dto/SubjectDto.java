package com.cnpmnc.roms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    private Long id;
    private String subjectCode;
    private String SubjectName;
}
