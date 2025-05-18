package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {

    List<RoomSchedule> findByRoomId(Long roomId);

    List<RoomSchedule> findByLecturerId(Long lecturerId);

    List<RoomSchedule> findByDateAndRoom(LocalDate date, Room room);

    List<RoomSchedule> findByLecturerIdAndDate(Long lecturerId, LocalDate date);
}
