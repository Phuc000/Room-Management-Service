package com.cnpmnc.roms.mapper;

import com.cnpmnc.roms.dto.RoomScheduleDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.entity.RoomSchedule;
import com.cnpmnc.roms.entity.Subject;

public class RoomScheduleMapper {

    public static RoomScheduleDto mapToRoomScheduleDto(RoomSchedule roomSchedule) {
        return new RoomScheduleDto(
                roomSchedule.getId(),
                roomSchedule.getRoom().getId(),
                roomSchedule.getLecturer().getId(),
                roomSchedule.getSubject().getId(),
                roomSchedule.getStartDate(),
                roomSchedule.getEndDate(),
                roomSchedule.getStartTime(),
                roomSchedule.getEndTime(),
                roomSchedule.getWeekdays());
    }

    public static RoomSchedule mapToRoomSchedule(RoomScheduleDto roomScheduleDto, Lecturer lecturer, Room room, Subject subject) {
        RoomSchedule roomSchedule = new RoomSchedule();
        roomSchedule.setId(roomScheduleDto.getId());
        roomSchedule.setStartTime(roomScheduleDto.getStartTime());
        roomSchedule.setEndTime(roomScheduleDto.getEndTime());
        roomSchedule.setStartDate(roomScheduleDto.getStartDate());
        roomSchedule.setEndDate(roomScheduleDto.getEndDate());
        roomSchedule.setWeekdays(roomScheduleDto.getWeekdays());

        roomSchedule.setLecturer(lecturer);
        roomSchedule.setRoom(room);
        roomSchedule.setSubject(subject);

        return roomSchedule;
    }
}
