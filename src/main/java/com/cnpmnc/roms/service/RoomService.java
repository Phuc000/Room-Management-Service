package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createRoom(RoomDto roomDto);

    List<RoomDto> getAllRooms();

    RoomDto getRoomById(Long id);

    List<String> getListBuildingByCampus(String campus);

    List<String> getListNameByBuildingAndCampus(String building, String campus);

    RoomDto updateRoom(Long id, RoomDto updatedRoomDto);

    void deleteRoom(Long id);

    RoomDto getIdByNameAndCampus(String name, String campus);
}
