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

    List<RoomScheduleDto> getSchedulesByDate(LocalDate date);
    
    List<RoomScheduleDto> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<RoomScheduleDto> getSchedulesByRoomAndDateRange(Long roomId, LocalDate startDate, LocalDate endDate);
    
    List<RoomScheduleDto> getSchedulesByBuildingAndDateRange(String building, LocalDate startDate, LocalDate endDate);
    
    List<RoomScheduleDto> getSchedulesByDateAndSessionRange(LocalDate date, int startSession, int endSession);
    
    List<RoomScheduleDto> getSchedulesByLecturerId(Long lecturerId);

    List<RoomScheduleDto> getSchedulesByLecturerIdAndDateRange(Long lecturerId, LocalDate startDate, LocalDate endDate);

    List<RoomScheduleDto> filterSchedules(
        String roomName, Long lecturerId, Long subjectId, String building, String campus, Integer floor,
        LocalDate startDate, LocalDate endDate, Integer startSession, Integer endSession,
        int page, int size);

    List<Integer> getAvailableTimeOfRoom(LocalDate date, Long id);

    List<RoomScheduleDto> getRoomScheduleByLecturerId(Long lecturerId);

    Boolean isAvailableTime(Long lecturerId, LocalDate date, int startSession, int endSession);

    Long getIdFromSubjectCode(String subjectId);

    void deleteScheduleByLecturerId(Long lecturerId, Long scheduleId);
}
