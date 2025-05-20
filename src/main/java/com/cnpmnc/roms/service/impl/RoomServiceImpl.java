package com.cnpmnc.roms.service.impl;

import com.cnpmnc.roms.dto.RoomDto;
import com.cnpmnc.roms.entity.Room;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.mapper.RoomMapper;
import com.cnpmnc.roms.mapper.RoomScheduleMapper;
import com.cnpmnc.roms.repository.RoomRepository;
import com.cnpmnc.roms.service.RoomService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = RoomMapper.mapToRoom(roomDto);
        Room newRoom = roomRepository.save(room);
        return RoomMapper.mapToRoomDto(newRoom);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("No room found with id" + id));
        return RoomMapper.mapToRoomDto(room);
    }

    @Override
    public RoomDto updateRoom(Long id, RoomDto updatedRoomDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("No room found with id" + id));
        room.setName(updatedRoomDto.getName());
        room.setNumber(updatedRoomDto.getNumber());
        room.setFloor(updatedRoomDto.getFloor());
        room.setBuilding(updatedRoomDto.getBuilding());
        Room updatedRoom = roomRepository.save(room);
        return RoomMapper.mapToRoomDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("No room found with id" + id));
        roomRepository.deleteById(id);
    }

    @Override
    public List<String> getListBuildingByCampus(String campus)
    {
        List<Room> rooms = roomRepository.getBuildingByCampus(campus);
        List<RoomDto> roomDtos = rooms.stream()
                                        .map(RoomMapper::mapToRoomDto)
                                        .collect(java.util.stream.Collectors.toList());
        return roomDtos.stream()
                .map(RoomDto::getBuilding)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getListNameByBuildingAndCampus(String building, String campus)
    {
        List<Room> rooms = roomRepository.getNameByBuildingAndCampus(building, campus);
        List<RoomDto> roomDtos = rooms.stream()
                .map(RoomMapper::mapToRoomDto)
                .collect(java.util.stream.Collectors.toList());
        return roomDtos.stream()
                .map(RoomDto::getName)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getIdByNameAndCampus(String name, String campus)
    {
        Room room = roomRepository.findByNameAndCampus(name, campus);

        return RoomMapper.mapToRoomDto(room);

    }


}
