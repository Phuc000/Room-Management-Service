package com.cnpmnc.roms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreationDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String position;
}
