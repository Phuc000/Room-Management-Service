package com.cnpmnc.roms.service.impl;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.entity.Subject;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.mapper.RoomScheduleMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.repository.RoomScheduleRepository;
import com.cnpmnc.roms.repository.SubjectRepository;
import com.cnpmnc.roms.service.RoomScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomScheduleServiceImpl implements RoomScheduleService {

    @Autowired
    private RoomScheduleRepository roomScheduleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public RoomScheduleDto createRoomSchedule(RoomScheduleDto roomScheduleDto) {
        // Validate DTO
        if (roomScheduleDto.getDate() == null) {
            throw new IllegalArgumentException("Date, start session, and end session must not be null");
        }
        Lecturer lecturer = lecturerRepository.findById(roomScheduleDto.getLecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + roomScheduleDto.getLecturerId()));

        Room room = roomRepository.findById(roomScheduleDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomScheduleDto.getRoomId()));

        Subject subject = subjectRepository.findById(roomScheduleDto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + roomScheduleDto.getSubjectId()));
        RoomSchedule roomSchedule = RoomScheduleMapper.mapToRoomSchedule(roomScheduleDto, lecturer, room, subject);
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
    public List<RoomScheduleDto> getRoomScheduleByLecturerId(Long lecturerId)
    {
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findByLecturerId(lecturerId);
        return roomSchedules.stream().map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public RoomScheduleDto updateRoomSchedule(Long id, RoomScheduleDto updatedRoomScheduleDto) {
        RoomSchedule roomSchedule = roomScheduleRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Room schedule not found with id " + id));
        roomSchedule.setDate(updatedRoomScheduleDto.getDate());
        roomSchedule.setStartSession(updatedRoomScheduleDto.getStartSession());
        roomSchedule.setEndSession(updatedRoomScheduleDto.getEndSession());
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
    public List<Integer> getAvailableTimeOfRoom(LocalDate date, Long id)
    {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 16; i++) {
            list.add(i);
        }
        Room room = roomRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("No room found with id" + id));
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findByDateAndId(date, room);
        if (roomSchedules.isEmpty()) {
            throw new ResourceNotFoundException("Room schedule not found for room: " + id);
        }

        List<RoomScheduleDto> roomSchedulesDtos = roomSchedules.stream().map(RoomScheduleMapper::mapToRoomScheduleDto)
                                                    .collect(java.util.stream.Collectors.toList());

        for (RoomScheduleDto roomSchedulesDto : roomSchedulesDtos) {
            int startSession = roomSchedulesDto.getStartSession();
            int endSession = roomSchedulesDto.getEndSession();
            for (int j = startSession; j <= endSession; j++) list.remove(Integer.valueOf(j));
            // Khong the trung lap
        }
        return list;
    }

    @Override
    public Boolean isAvailableTime(Long lecturerId, LocalDate date, Long roomId,  int startSession, int endSession)
    {
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findByLecturerIdAndDate(lecturerId, date);
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 16; i++) {
            list.add(i);
        }
        List<RoomScheduleDto> roomSchedulesDtos = roomSchedules.stream().map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(java.util.stream.Collectors.toList());

        for (RoomScheduleDto roomSchedulesDto : roomSchedulesDtos) {
            int startSessionBook = roomSchedulesDto.getStartSession();
            int endSessionBook = roomSchedulesDto.getEndSession();
            for (int j = startSessionBook; j <= endSessionBook; j++)
            {
                try {
                    list.remove(Integer.valueOf(j));
                } catch (Exception e) {
                    System.out.println("Log: This is overlaped schedule");
                }
            }
        }
        for (int i = startSession; i <= endSession; i++)
        {
            try {
                list.remove(Integer.valueOf(i));
            } catch (Exception e) {
                System.out.println("Log: This is overlapped schedule");
                return false;
            }
        }
        return true;
    }

}
