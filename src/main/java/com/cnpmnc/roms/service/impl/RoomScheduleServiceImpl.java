package com.cnpmnc.roms.service.impl;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.entity.Subject;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.mapper.RoomScheduleMapper;
import com.cnpmnc.roms.mapper.SubjectMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.repository.RoomScheduleRepository;
import com.cnpmnc.roms.repository.SubjectRepository;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;


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
    public List<RoomScheduleDto> getSchedulesByDate(LocalDate date) {
        List<RoomSchedule> schedules = roomScheduleRepository.findByDate(date);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomScheduleDto> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<RoomSchedule> schedules = roomScheduleRepository.findByDateBetween(startDate, endDate);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomScheduleDto> getSchedulesByRoomAndDateRange(Long roomId, LocalDate startDate, LocalDate endDate) {
        // Verify room exists
        roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomId));
                
        List<RoomSchedule> schedules = roomScheduleRepository.findByRoomIdAndDateBetween(roomId, startDate, endDate);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomScheduleDto> getSchedulesByBuildingAndDateRange(String building, LocalDate startDate, LocalDate endDate) {
        List<RoomSchedule> schedules = roomScheduleRepository.findByBuildingAndDateBetween(building, startDate, endDate);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomScheduleDto> getSchedulesByDateAndSessionRange(LocalDate date, int startSession, int endSession) {
        List<RoomSchedule> schedules = roomScheduleRepository.findByDateAndStartSessionLessThanEqualAndEndSessionGreaterThanEqual(
                date, endSession, startSession);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomScheduleDto> getSchedulesByLecturerId(Long lecturerId) {
        lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + lecturerId));
                
        List<RoomSchedule> schedules = roomScheduleRepository.findByLecturerId(lecturerId);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
    }


        @Override
        public List<RoomScheduleDto> getSchedulesByLecturerIdAndDateRange(Long lecturerId, LocalDate startDate, LocalDate endDate) {
        lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + lecturerId));
                
        List<RoomSchedule> schedules = roomScheduleRepository.findByLecturerIdAndDateBetween(lecturerId, startDate, endDate);
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
        }


        @Override
        public List<RoomScheduleDto> filterSchedules(
                Long roomId, Long lecturerId, Long subjectId, String building, String campus, Integer floor,
                LocalDate startDate, LocalDate endDate, Integer startSession, Integer endSession,
                int page, int size) {
        
        List<RoomSchedule> schedules;
        
        Specification<RoomSchedule> spec = Specification.where(null);
        
        if (roomId != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("room").get("id"), roomId));
        }
        
        if (lecturerId != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("lecturer").get("id"), lecturerId));
        }
        
        if (subjectId != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("subject").get("id"), subjectId));
        }
        
        if (building != null && !building.isEmpty()) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("room").get("building"), building));
        }
        
        if (campus != null && !campus.isEmpty()) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("room").get("campus"), campus));
        }
        
        if (floor != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("room").get("floor"), floor));
        }
        
        if (startDate != null && endDate != null) {
                spec = spec.and((root, query, cb) -> 
                cb.between(root.get("date"), startDate, endDate));
        } else if (startDate != null) {
                spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("date"), startDate));
        } else if (endDate != null) {
                spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("date"), endDate));
        }
        
        if (startSession != null && endSession != null) {
                // Sessions overlap if:
                // startA <= endB AND startB <= endA
                spec = spec.and((root, query, cb) -> 
                cb.and(
                        cb.lessThanOrEqualTo(root.get("startSession"), endSession),
                        cb.greaterThanOrEqualTo(root.get("endSession"), startSession)
                ));
        } else if (startSession != null) {
                spec = spec.and((root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("startSession"), startSession));
        } else if (endSession != null) {
                spec = spec.and((root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("endSession"), endSession));
        }
        
        // Execute query with pagination
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomSchedule> result = roomScheduleRepository.findAll(spec, pageable);
        schedules = result.getContent();
        
        return schedules.stream()
                .map(RoomScheduleMapper::mapToRoomScheduleDto)
                .collect(Collectors.toList());
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
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findByDateAndRoom(date, room);
        if (roomSchedules.isEmpty()) {
            return list;
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
    public Boolean isAvailableTime(Long lecturerId, LocalDate date, int startSession, int endSession)
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
                    System.out.println("Log: This is overlapped schedule");
                }
            }
        }
        for (int i = startSession; i <= endSession; i++)
        {
            if (!list.contains(i))
                return false;
            try {
                list.remove(Integer.valueOf(i));
            } catch (Exception e) {
                System.out.println("Log: This is overlapped schedule");
                return false;
            }
        }
        return true;
    }

    @Override
    public Long getIdFromSubjectCode(String subjectCode)
    {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode)
                                            .orElseThrow(() -> new ResourceNotFoundException("Subject not found with code " + subjectCode));
        return SubjectMapper.mapToSubjectDto(subject).getId();
    }

}
