package com.cnpmnc.roms.dto;

import java.time.LocalDate;

public class ScheduleFilterDto {
    private Long roomId;
    private String building;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer floor;
    
    public ScheduleFilterDto() {
    }
    
    public ScheduleFilterDto(Long roomId, String building, LocalDate startDate, LocalDate endDate, Integer floor) {
        this.roomId = roomId;
        this.building = building;
        this.startDate = startDate;
        this.endDate = endDate;
        this.floor = floor;
    }
    
    public Long getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
    public String getBuilding() {
        return building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public Integer getFloor() {
        return floor;
    }
    
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}