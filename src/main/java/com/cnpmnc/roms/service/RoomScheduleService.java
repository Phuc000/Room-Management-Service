package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface RoomScheduleService {

    @Transactional
    RoomScheduleDto createRoomSchedule(RoomScheduleDto roomScheduleDto);

    List<RoomScheduleDto> getAllRoomSchedules();

    RoomScheduleDto getRoomScheduleById(Long id);

    RoomScheduleDto updateRoomSchedule(Long id, RoomScheduleDto updatedRoomScheduleDto);

    void deleteRoomSchedule(Long id);

    List<Integer> getAvailableTimeOfRoom(LocalDate date, Long id);

    List<RoomScheduleDto> getRoomScheduleByLecturerId(Long lecturerId);

    Boolean isAvailableTime(Long lecturerId, LocalDate date, Long roomId, int startSession, int endSession);
}
