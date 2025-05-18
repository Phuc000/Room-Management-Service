package com.cnpmnc.roms.controller;

import com.cnpmnc.roms.dto.BookingRequestDto;
import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.repository.UserRepository;
import com.cnpmnc.roms.security.JwtUtil;
import com.cnpmnc.roms.service.RoomScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/roomschedules")
public class RoomScheduleController {

    @Autowired
    private RoomScheduleService roomScheduleService;

    @Autowired
    private RoomRepository roomRepository;

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
                                                           @RequestParam int startSession,
                                                           @RequestParam int endSession,
                                                           @RequestParam String campus,
                                                           @RequestParam String building,
                                                           @RequestParam String name)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long roomId = roomRepository.findByNameAndBuildingAndCampus(name, building, campus).getId();
        Boolean isAvailable = roomScheduleService.isAvailableTime(userRepository.findByEmail(userName).getId(),
                                                                    date, roomId, startSession, endSession);
        if (isAvailable)
            return ResponseEntity.ok("Success");
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Schedule overlapped");
    }

    @PostMapping("/booking")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public ResponseEntity<String> bookRoomScheduleInfoById(HttpServletRequest request,
                                                           @RequestBody BookingRequestDto bookingRequest)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long roomId = roomRepository.findByNameAndBuildingAndCampus(bookingRequest.getName(), bookingRequest.getBuilding(), bookingRequest.getCampus())
                                    .getId();
        RoomScheduleDto roomScheduleDto = new RoomScheduleDto(null,
                                                                roomId,
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


}
