package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createRoom(RoomDto roomDto);

    List<RoomDto> getAllRooms();

    RoomDto getRoomById(Long id);

    RoomDto updateRoom(Long id, RoomDto updatedRoomDto);

    void deleteRoom(Long id);
}
