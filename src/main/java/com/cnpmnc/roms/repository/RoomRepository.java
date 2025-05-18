package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByNameAndBuildingAndCampus(String name, String building, String campus);
}
