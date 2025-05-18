package com.cnpmnc.roms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
//  private Long id;
    private String name;
    private Integer number;
    private Integer floor;
    private String building;
    private String campus;
}
