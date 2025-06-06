package com.cnpmnc.roms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer number;

    private Integer floor;

    private String building;

    private String campus;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomSchedule> roomSchedules;

    public Room(String name, Integer number, Integer floor, String building, String campus) {
//        this.id = id;
        this.name = name;
        this.number = number;
        this.floor = floor;
        this.building = building;
        this.campus = campus;
    }

}
