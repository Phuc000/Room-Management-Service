package com.cnpmnc.roms.service;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.dto.ScheduleFilterDto;
import java.time.LocalDate;
import java.util.List;


public interface RoomScheduleService {

    RoomScheduleDto createRoomSchedule(RoomScheduleDto roomScheduleDto);

    List<RoomScheduleDto> getAllRoomSchedules();

    RoomScheduleDto getRoomScheduleById(Long id);

    RoomScheduleDto updateRoomSchedule(Long id, RoomScheduleDto updatedRoomScheduleDto);

    void deleteRoomSchedule(Long id);
    
    // New methods
    List<RoomScheduleDto> getSchedulesByFilters(ScheduleFilterDto filterDto);
    
    List<RoomScheduleDto> getSchedulesForDay(LocalDate date);
    
    List<RoomScheduleDto> getSchedulesForWeek(LocalDate startOfWeek);
    
    List<RoomScheduleDto> getLecturerSchedules(Long lecturerId);
}
