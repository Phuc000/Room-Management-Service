package com.cnpmnc.roms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "lecturers")
public class Lecturer extends BaseUser {

    private String department;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<RoomSchedule> roomSchedules;

}
