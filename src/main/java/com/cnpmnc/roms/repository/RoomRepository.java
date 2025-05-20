package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByNameAndBuildingAndCampus(String name, String building, String campus);

    Room findByNameAndCampus(String name, String campus);

    List<Room> getBuildingByCampus(String campus);

    List<Room> getNameByBuildingAndCampus(String building, String campus);
}
