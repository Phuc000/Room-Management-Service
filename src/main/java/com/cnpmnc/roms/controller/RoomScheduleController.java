package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.BookingRequestDto;
import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.repository.UserRepository;
import com.cnpmnc.roms.security.JwtUtil;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/roomschedules")
public class RoomScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;


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

    @GetMapping("/getschedule")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<RoomScheduleDto>> getRoomScheduleById() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RoomScheduleDto> roomScheduleDto = roomScheduleService.getRoomScheduleByLecturerId(userRepository.findByEmail(userName).getId());
        return ResponseEntity.ok(roomScheduleDto);
    }


    @GetMapping("/isAvailable")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<String> bookRoomScheduleInfoById(HttpServletRequest request,
                                                           @RequestParam LocalDate date,
                                                           @RequestParam Long roomId,
                                                           @RequestParam int startSession,
                                                           @RequestParam int endSession)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Boolean isAvailable = roomScheduleService.isAvailableTime(userRepository.findByEmail(userName).getId(),
                                                                    date, roomId, startSession, endSession);
        if (isAvailable)
            return ResponseEntity.ok("Successfully booked!");
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Schedule overlapped");
    }

    @PostMapping("/booking")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<String> bookRoomScheduleInfoById(HttpServletRequest request,
                                                           @RequestBody BookingRequestDto bookingRequest)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        RoomScheduleDto roomScheduleDto = new RoomScheduleDto(null,
                                                                bookingRequest.getRoomId(),
                                                                userRepository.findByEmail(userName).getId(),
                                                                bookingRequest.getSubjectId(),
                                                                bookingRequest.getDate(),
                                                                bookingRequest.getStartSession(),
                                                                bookingRequest.getEndSession());

        try {
            RoomScheduleDto newRoomScheduleDto = roomScheduleService.createRoomSchedule(roomScheduleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking successful!");
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

    @GetMapping("/available/{date}")
    //    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<List<Integer>> getInformationByDateAndId (@PathVariable("date") LocalDate date, @RequestParam("id") Long id) {
        return ResponseEntity.ok(roomScheduleService.getAvailableTimeOfRoom(date, id));
    }



    // @GetMapping("/date/{date}")
    // public ResponseEntity<List<RoomScheduleDto>> getSchedulesByDate(
    //         @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    //     List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByDate(date);
    //     return ResponseEntity.ok(schedules);
    // }

    // @GetMapping("/date-range")
    // public ResponseEntity<List<RoomScheduleDto>> getSchedulesByWeek(
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    //     List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByDateRange(startDate, endDate);
    //     return ResponseEntity.ok(schedules);
    // }

    // @GetMapping("/room/{roomId}/date-range")
    // public ResponseEntity<List<RoomScheduleDto>> getSchedulesByRoomAndDateRange(
    //         @PathVariable Long roomId,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    //     List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByRoomAndDateRange(roomId, startDate, endDate);
    //     return ResponseEntity.ok(schedules);
    // }

    // @GetMapping("/building/{building}/date-range")
    // public ResponseEntity<List<RoomScheduleDto>> getSchedulesByBuildingAndDateRange(
    //         @PathVariable String building,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    //     List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByBuildingAndDateRange(building, startDate, endDate);
    //     return ResponseEntity.ok(schedules);
    // }

    // @GetMapping("/date/{date}/sessions")
    // public ResponseEntity<List<RoomScheduleDto>> getSchedulesByDateAndSessionRange(
    //         @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
    //         @RequestParam int startSession,
    //         @RequestParam int endSession) {
    //     List<RoomScheduleDto> schedules = roomScheduleService.getSchedulesByDateAndSessionRange(date, startSession, endSession);
    //     return ResponseEntity.ok(schedules);
    // }

    @GetMapping("/filter")
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

    @GetMapping("/quick-filter")
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
