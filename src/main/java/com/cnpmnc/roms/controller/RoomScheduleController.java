package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.BookingRequestDto;
import com.cnpmnc.roms.dto.CampusDto;
import com.cnpmnc.roms.dto.DeleteRequestDto;
import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.entity.Subject;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.repository.SubjectRepository;
import com.cnpmnc.roms.repository.UserRepository;
import com.cnpmnc.roms.security.JwtUtil;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.*;

import com.cnpmnc.roms.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/roomschedules")
public class RoomScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<RoomScheduleDto>> getAllRoomSchedules() {
        List<RoomScheduleDto> roomScheduleDtos = roomScheduleService.getAllRoomSchedules();
        return ResponseEntity.ok(roomScheduleDtos);
    }
//
//    @PostMapping
//    @PreAuthorize("hasRole('ROLE_LECTURER')")
//    public ResponseEntity<RoomScheduleDto> createRoomSchedule(@RequestBody RoomScheduleDto roomScheduleDto) {
//        RoomScheduleDto newRoomScheduleDto = roomScheduleService.createRoomSchedule(roomScheduleDto);
//        return new ResponseEntity<>(newRoomScheduleDto, HttpStatus.CREATED);
//    }

    @PostMapping("/getschedule")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<List<RoomScheduleDto>> getRoomScheduleById() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RoomScheduleDto> roomScheduleDto = roomScheduleService.getRoomScheduleByLecturerId(userRepository.findByEmail(userName).getId());
        return ResponseEntity.ok(roomScheduleDto);
    }


    @PostMapping("/isAvailable")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<?> checkRoomScheduleInfoById(HttpServletRequest request,
                                                           @RequestParam LocalDate date,
                                                           @RequestParam int startSession,
                                                           @RequestParam int endSession)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Boolean isAvailable = roomScheduleService.isAvailableTime(userRepository.findByEmail(userName).getId(),
                                                                    date, startSession, endSession);
        if (isAvailable)
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Success"));
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Schedule Overlapped"));
    }

//    @DeleteMapping("/deletebooking")
//    @PreAuthorize("hasRole('LECTURER')")
//    public ResponseEntity<?> deleteRoomSchedule(@RequestBody DeleteRequestDto deleteRequestDto)
//    {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        Long id = userRepository.findByEmail(userName).getId();
//        try
//        {
//
//        }
//    }

    @DeleteMapping("/deletebooking")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<?> deleteRoomSchedule(@RequestParam Long id)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long lecturerId = userRepository.findByEmail(userName).getId();
        try {
            roomScheduleService.deleteScheduleByLecturerId(lecturerId, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Deleted"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (AccessDeniedException | InternalAuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred"));
        }

    }

    @PutMapping("/updateBooking")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<?> updateRoomSchedule(@RequestParam Long id,
                                                @RequestParam LocalDate date,
                                                @RequestParam int startSession,
                                                @RequestParam int endSession)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long lecturerId = userRepository.findByEmail(userName).getId();
        try {
            roomScheduleService.deleteScheduleByLecturerId(lecturerId, id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (AccessDeniedException | InternalAuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred"));
        }

        Boolean isAvailable = roomScheduleService.isAvailableTime(lecturerId,
                                                    date, startSession, endSession);
        if (isAvailable)
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Success"));
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Schedule Overlapped"));

    }

    @PostMapping("/booking")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<?> bookRoomSchedule(HttpServletRequest request,
                                                   @RequestBody BookingRequestDto bookingRequest)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long roomId = roomRepository.findByNameAndBuildingAndCampus(bookingRequest.getName(), bookingRequest.getBuilding(), bookingRequest.getCampus())
                                    .getId();
        Long subjectId = roomScheduleService.getIdFromSubjectCode(bookingRequest.getSubjectCode());
        RoomScheduleDto roomScheduleDto = new RoomScheduleDto(null,
                                                                roomId,
                                                                userRepository.findByEmail(userName).getId(),
                                                                subjectId,
                                                                bookingRequest.getDate(),
                                                                bookingRequest.getStartSession(),
                                                                bookingRequest.getEndSession());
        try {
            RoomScheduleDto newRoomScheduleDto = roomScheduleService.createRoomSchedule(roomScheduleDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking failed: " + e.getMessage());
        }
    }


//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_LECTURER')")
//    public ResponseEntity<String> deleteRoomScheduleById(@PathVariable Long id) {
//        roomScheduleService.deleteRoomSchedule(id);
//        return new ResponseEntity<>("Room schedule deleted successfully!", HttpStatus.OK);
//    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_LECTURER')")
//    public ResponseEntity<RoomScheduleDto> updateRoomScheduleById(@PathVariable Long id, @RequestBody RoomScheduleDto updatedRoomScheduleDto) {
//        RoomScheduleDto roomScheduleDto = roomScheduleService.updateRoomSchedule(id, updatedRoomScheduleDto);
//        return ResponseEntity.ok(roomScheduleDto);
//    }

    @PostMapping("/available/{date}")
    // @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<Integer>> getInformationByDateAndId (@PathVariable("date") LocalDate date,
                                                                    @RequestParam("campus") String campus,
                                                                    @RequestParam("name") String name)
    {
        Long id = roomService.getIdByNameAndCampus(name, campus).getId();
        return ResponseEntity.ok(roomScheduleService.getAvailableTimeOfRoom(date, id));
    }

    @PostMapping("/buildingByCampus")
    // @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<Set<String>> getBuildingInCampus (@RequestParam String campus)
    {
        return ResponseEntity.ok(new HashSet<>(roomService.getListBuildingByCampus(campus)));
    }

    @PostMapping("/nameByBuilding")
    // @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<String>> getNameInBuilding (@RequestParam("building") String building,
                                                           @RequestParam("campus") String campus)
    {
        return ResponseEntity.ok(roomService.getListNameByBuildingAndCampus(building, campus));
    }

    @PostMapping("/getsubject/{subjectCode}")
    // @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<?> getSubjectName (@PathVariable("subjectCode") String subjectCode)
    {
        Optional<Subject> subject = subjectRepository.findBySubjectCode(subjectCode);

        if (subject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("subjectName", "Not exist this subject"));
        }

        return ResponseEntity.ok(Map.of("subjectName", subject.get().getSubjectName()));
    }

    
    @PostMapping("/filter")
    public ResponseEntity<List<RoomScheduleDto>> filterSchedules(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Long lecturerId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String building,
            @RequestParam(required = false) String campus,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer startSession,
            @RequestParam(required = false) Integer endSession,
            @RequestParam(required = false) Integer weekNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // Handle "current week" request
        if (weekNumber != null && weekNumber == 0) {
            LocalDate now = LocalDate.now();
            startDate = now.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            endDate = startDate.plusDays(6);
        } else if (weekNumber != null) {
            LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
            LocalDate firstMondayOfYear = firstDayOfYear.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.MONDAY));
            startDate = firstMondayOfYear.plusWeeks(weekNumber - 1);
            endDate = startDate.plusDays(6);
        }
        
        List<RoomScheduleDto> schedules = roomScheduleService.filterSchedules(
                roomId, lecturerId, subjectId, building, campus, floor,
                startDate, endDate, startSession, endSession, page, size);
        
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/quick-filter")
    public ResponseEntity<List<RoomScheduleDto>> quickFilter(
            @RequestParam(required = true) String filterType) {
        
        LocalDate today = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;
        
        switch (filterType) {
            case "today":
                startDate = today;
                endDate = today;
                break;
            case "tomorrow":
                startDate = today.plusDays(1);
                endDate = startDate;
                break;
            case "this-week":
                startDate = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                endDate = startDate.plusDays(6);
                break;
            case "next-week":
                startDate = today.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.MONDAY))
                                .plusWeeks(1);
                endDate = startDate.plusDays(6);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        
        List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByDateRange(startDate, endDate);
        return ResponseEntity.ok(schedules);
    }
}
