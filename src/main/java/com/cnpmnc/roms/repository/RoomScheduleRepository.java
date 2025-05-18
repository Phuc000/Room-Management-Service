package com.cnpmnc.roms.repository;

import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;


public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long>, JpaSpecificationExecutor<RoomSchedule> {

    List<RoomSchedule> findByRoomId(Long roomId);

    List<RoomSchedule> findByLecturerId(Long lecturerId);

    List<RoomSchedule> findByDateAndRoom(LocalDate date, Room room);

    List<RoomSchedule> findByLecturerIdAndDate(Long lecturerId, LocalDate date);

    List<RoomSchedule> findByDate(LocalDate date);
    
    List<RoomSchedule> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<RoomSchedule> findByRoomIdAndDateBetween(Long roomId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT rs FROM RoomSchedule rs JOIN rs.room r WHERE r.building = :building AND rs.date BETWEEN :startDate AND :endDate")
    List<RoomSchedule> findByBuildingAndDateBetween(@Param("building") String building, 
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
    
    List<RoomSchedule> findByDateAndStartSessionLessThanEqualAndEndSessionGreaterThanEqual(
            LocalDate date, int endSession, int startSession);


    List<RoomSchedule> findByLecturerIdAndDateBetween(Long lecturerId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT rs FROM RoomSchedule rs JOIN rs.room r WHERE r.campus = :campus AND rs.date BETWEEN :startDate AND :endDate")
    List<RoomSchedule> findByCampusAndDateBetween(
        @Param("campus") String campus, 
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    @Query("SELECT rs FROM RoomSchedule rs JOIN rs.room r WHERE r.floor = :floor AND rs.date BETWEEN :startDate AND :endDate")
    List<RoomSchedule> findByFloorAndDateBetween(
        @Param("floor") Integer floor, 
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
