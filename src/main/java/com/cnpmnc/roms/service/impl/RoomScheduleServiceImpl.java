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
        System.out.println("This line");
        // Validate DTO
        if (roomScheduleDto.getDate() == null) {
            throw new IllegalArgumentException("Date, start session, and end session must not be null");
        }
        System.out.println("This line");
        Lecturer lecturer = lecturerRepository.findById(roomScheduleDto.getLecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + roomScheduleDto.getLecturerId()));
        System.out.println("This line");

        Room room = roomRepository.findById(roomScheduleDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomScheduleDto.getRoomId()));
        System.out.println("This line");

        Subject subject = subjectRepository.findById(roomScheduleDto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + roomScheduleDto.getSubjectId()));
        System.out.println("This line");
        RoomSchedule roomSchedule = RoomScheduleMapper.mapToRoomSchedule(roomScheduleDto, lecturer, room, subject);
        System.out.println("This line");
        RoomSchedule savedRoomSchedule = roomScheduleRepository.save(roomSchedule);
        System.out.println("This line");
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
}
