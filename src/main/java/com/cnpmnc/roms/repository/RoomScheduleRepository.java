package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.RoomSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {

    List<RoomSchedule> findByRoomId(Long roomId);

    List<RoomSchedule> findByLecturerId(Long lecturerId);
    
    @Query(value = "SELECT rs FROM RoomSchedule rs WHERE " +
           "(:roomId IS NULL OR rs.room.id = :roomId) AND " +
           "(:building IS NULL OR rs.room.building = :building) AND " +
           "(CAST(:startDate AS string) IS NULL AND CAST(:endDate AS string) IS NULL OR " +
           "CAST(:startDate AS string) IS NULL AND rs.endDate >= :endDate OR " +
           "CAST(:endDate AS string) IS NULL AND rs.startDate <= :startDate OR " +
           "rs.startDate <= :endDate AND rs.endDate >= :startDate)")
    List<RoomSchedule> findSchedulesByFilters(
            @Param("roomId") Long roomId,
            @Param("building") String building,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
