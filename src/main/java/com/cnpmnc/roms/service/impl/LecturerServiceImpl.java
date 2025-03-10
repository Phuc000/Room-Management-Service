package com.cnpmnc.roms.service.impl;

import com.cnpmnc.roms.dto.LecturerDto;
import com.cnpmnc.roms.entity.Lecturer;
import com.cnpmnc.roms.exception.ResourceNotFoundException;
import com.cnpmnc.roms.mapper.LecturerMapper;
import com.cnpmnc.roms.repository.LecturerRepository;
import com.cnpmnc.roms.service.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    @Override
    public LecturerDto createLecturer(LecturerDto lecturerDto) {

        Lecturer lecturer = LecturerMapper.mapToLecturer(lecturerDto);
        Lecturer savedLecturer = lecturerRepository.save(lecturer);
        return LecturerMapper.mapToLecturerDto(savedLecturer);
    }

    @Override
    public List<LecturerDto> getAllLecturers() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return  lecturers.stream().map(LecturerMapper::mapToLecturerDto)
                .collect(Collectors.toList());
    }

    @Override
    public LecturerDto getLecturerById(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Lecturer not found with id " + id));

        return LecturerMapper.mapToLecturerDto(lecturer);
    }

    @Override
    public LecturerDto updateLecturer(Long id, LecturerDto updatedLecturerDto) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Lecturer not found with id " + id));
        lecturer.setFirstName(updatedLecturerDto.getFirstName());
        lecturer.setLastName(updatedLecturerDto.getLastName());
        lecturer.setEmail(updatedLecturerDto.getEmail());
        lecturer.setDepartment(updatedLecturerDto.getDepartment());

        // Save the updated lecturer
        Lecturer updatedLecturer = lecturerRepository.save(lecturer);
        return LecturerMapper.mapToLecturerDto(updatedLecturer);
    }

    @Override
    public void deleteLecturer(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Lecturer not found with id" + id));
        lecturerRepository.deleteById(id);
    }
}
