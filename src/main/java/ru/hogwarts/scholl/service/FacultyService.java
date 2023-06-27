package ru.hogwarts.scholl.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.scholl.dto.FacultyDtoIn;
import ru.hogwarts.scholl.dto.FacultyDtoOut;
import ru.hogwarts.scholl.dto.StudentDtoOut;
import ru.hogwarts.scholl.exception.FacultyNameNotFoundException;
import ru.hogwarts.scholl.exception.FacultyNotFoundException;
import ru.hogwarts.scholl.mapper.FacultyMapper;
import ru.hogwarts.scholl.mapper.StudentMapper;
import ru.hogwarts.scholl.model.Faculty;
import ru.hogwarts.scholl.repository.FacultyRepository;
import ru.hogwarts.scholl.repository.StudentRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public FacultyService(FacultyRepository facultyRepository,
                          FacultyMapper facultyMapper,
                          StudentRepository studentRepository,
                          StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public FacultyDtoOut create(FacultyDtoIn facultyDtoIn) {
        return facultyMapper.toDto(
                facultyRepository.save(
                        facultyMapper.toEntity(facultyDtoIn)
                )
        );
    }

    public FacultyDtoOut get(long id) {
        return facultyMapper.toDto(
                facultyRepository.findById(id)
                        .orElseThrow(() -> new FacultyNotFoundException(id)));
    }

    public FacultyDtoOut update(long id, FacultyDtoIn facultyDtoIn) {
        Faculty updatedFaculty = facultyRepository.findById(id).
                map(faculty -> {
                    faculty.setName(facultyDtoIn.getName());
                    faculty.setColor(facultyDtoIn.getColor());
                    return faculty;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.save(updatedFaculty);
        return facultyMapper.toDto(updatedFaculty);
    }

    public FacultyDtoOut delete(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.deleteById(id);
        return facultyMapper.toDto(faculty);
    }

    public Collection<FacultyDtoOut> findByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(
                        colorOrName, colorOrName
                ).stream()
                .map(facultyMapper::toDto)
                .toList();
    }

    public Collection<StudentDtoOut> findStudentsByFaculty(String facultyName) {
        return facultyRepository.findByNameIgnoreCase(facultyName)
                .map(Faculty::getId)
                .map(studentRepository::findAllByFaculty_id)
                .orElseThrow(()->new FacultyNameNotFoundException(facultyName)).stream()
                .map(studentMapper::toDto)
                .toList();
    }
}
