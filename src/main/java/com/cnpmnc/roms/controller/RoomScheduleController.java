package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.service.RoomScheduleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/roomschedules")
public class RoomScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;

    @GetMapping
    public ResponseEntity<List<RoomScheduleDto>> getAllRoomSchedules() {
        List<RoomScheduleDto> roomScheduleDtos = roomScheduleService.getAllRoomSchedules();
        return ResponseEntity.ok(roomScheduleDtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<RoomScheduleDto> createRoomSchedule(@RequestBody RoomScheduleDto roomScheduleDto) {
        RoomScheduleDto newRoomScheduleDto = roomScheduleService.createRoomSchedule(roomScheduleDto);
        return new ResponseEntity<>(newRoomScheduleDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomScheduleDto> getRoomScheduleById(@PathVariable Long id) {
        RoomScheduleDto roomScheduleDto = roomScheduleService.getRoomScheduleById(id);
        return ResponseEntity.ok(roomScheduleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<String> deleteRoomScheduleById(@PathVariable Long id) {
        roomScheduleService.deleteRoomSchedule(id);
        return new ResponseEntity<>("Room schedule deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<RoomScheduleDto> updateRoomScheduleById(@PathVariable Long id, @RequestBody RoomScheduleDto updatedRoomScheduleDto) {
        RoomScheduleDto roomScheduleDto = roomScheduleService.updateRoomSchedule(id, updatedRoomScheduleDto);
        return ResponseEntity.ok(roomScheduleDto);
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
