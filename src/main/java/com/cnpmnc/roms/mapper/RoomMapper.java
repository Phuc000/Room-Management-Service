package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.RoomDto;
import com.cnpmnc.roms.entity.Room;

public class RoomMapper {

    public static RoomDto mapToRoomDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getName(),
                room.getNumber(),
                room.getFloor(),
                room.getBuilding());
    }

    public static Room mapToRoom(RoomDto roomDto) {
        return new Room(
                roomDto.getId(),
                roomDto.getName(),
                roomDto.getNumber(),
                roomDto.getFloor(),
                roomDto.getBuilding());
    }
}
