package com.cnpmnc.roms.service.impl;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.dto.ScheduleFilterDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.mapper.RoomScheduleMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.repository.RoomScheduleRepository;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RoomScheduleServiceImpl implements RoomScheduleService {

    private final RoomScheduleRepository roomScheduleRepository;
    private final RoomRepository roomRepository;
    private final LecturerRepository lecturerRepository;

    public RoomScheduleServiceImpl(RoomScheduleRepository roomScheduleRepository, 
                                  RoomRepository roomRepository, 
                                  LecturerRepository lecturerRepository) {
        this.roomScheduleRepository = roomScheduleRepository;
        this.roomRepository = roomRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public RoomScheduleDto createRoomSchedule(RoomScheduleDto roomScheduleDto) {
        // Validate DTO
        if(roomScheduleDto.getStartDate().isAfter(roomScheduleDto.getEndDate()) ||
                roomScheduleDto.getStartTime().isAfter(roomScheduleDto.getEndTime())) {
            throw new IllegalArgumentException("Invalid date or time range");
        }

        Lecturer lecturer = lecturerRepository.findById(roomScheduleDto.getLecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + roomScheduleDto.getLecturerId()));
        Room room = roomRepository.findById(roomScheduleDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomScheduleDto.getRoomId()));
        RoomSchedule roomSchedule = RoomScheduleMapper.mapToRoomSchedule(roomScheduleDto, lecturer, room);
        RoomSchedule savedRoomSchedule = roomScheduleRepository.save(roomSchedule);
        return RoomScheduleMapper.mapToRoomScheduleDto(savedRoomSchedule);
    }

    @Override
    public List<RoomScheduleDto> getAllRoomSchedules() {
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findAll();
        return roomSchedules.stream().map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public RoomScheduleDto getRoomScheduleById(Long id) {
        RoomSchedule roomSchedule = roomScheduleRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Room schedule not found with id " + id));
        return RoomScheduleMapper.mapToRoomScheduleDto(roomSchedule);
    }

    @Override
    public RoomScheduleDto updateRoomSchedule(Long id, RoomScheduleDto updatedRoomScheduleDto) {
        RoomSchedule roomSchedule = roomScheduleRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Room schedule not found with id " + id));
        roomSchedule.setStartDate(updatedRoomScheduleDto.getStartDate());
        roomSchedule.setEndDate(updatedRoomScheduleDto.getEndDate());
        roomSchedule.setStartTime(updatedRoomScheduleDto.getStartTime());
        roomSchedule.setEndTime(updatedRoomScheduleDto.getEndTime());
        roomSchedule.setWeekdays(updatedRoomScheduleDto.getWeekdays());
        RoomSchedule updatedRoomSchedule = roomScheduleRepository.save(roomSchedule);
        return RoomScheduleMapper.mapToRoomScheduleDto(updatedRoomSchedule);
    }

    @Override
    public void deleteRoomSchedule(Long id) {
        RoomSchedule roomSchedule = roomScheduleRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Room schedule not found with id " + id));
        roomScheduleRepository.deleteById(id);
    }
    
    @Override
    public List<RoomScheduleDto> getSchedulesByFilters(ScheduleFilterDto filterDto) {
        // If we need to filter by floor which isn't in the query
        List<RoomSchedule> schedules = roomScheduleRepository.findSchedulesByFilters(
                filterDto.getRoomId(),
                filterDto.getBuilding(),
                filterDto.getStartDate(),
                filterDto.getEndDate()
        );
        
        // Apply floor filter in memory if specified
        if (filterDto.getFloor() != null) {
            schedules = schedules.stream()
                .filter(schedule -> schedule.getRoom().getFloor().equals(filterDto.getFloor()))
                .collect(Collectors.toList());
        }
        
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RoomScheduleDto> getSchedulesForDay(LocalDate date) {
        return getSchedulesByFilters(new ScheduleFilterDto(
                null, null, date, date, null
        ));
    }
    
    @Override
    public List<RoomScheduleDto> getSchedulesForWeek(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return getSchedulesByFilters(new ScheduleFilterDto(
                null, null, startOfWeek, endOfWeek, null
        ));
    }
    
    @Override
    public List<RoomScheduleDto> getLecturerSchedules(Long lecturerId) {
        // Check if lecturer exists
        lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + lecturerId));
                
        List<RoomSchedule> lecturerSchedules = roomScheduleRepository.findByLecturerId(lecturerId);
        return lecturerSchedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }
}
