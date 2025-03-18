package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.RoomScheduleDto;

import java.util.List;

public interface RoomScheduleService {

    RoomScheduleDto createRoomSchedule(RoomScheduleDto roomScheduleDto);

    List<RoomScheduleDto> getAllRoomSchedules();

    RoomScheduleDto getRoomScheduleById(Long id);

    RoomScheduleDto updateRoomSchedule(Long id, RoomScheduleDto updatedRoomScheduleDto);

    void deleteRoomSchedule(Long id);
}
