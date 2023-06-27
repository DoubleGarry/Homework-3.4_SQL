package ru.hogwarts.scholl.service;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.scholl.dto.FacultyDtoOut;
import ru.hogwarts.scholl.dto.StudentDtoIn;
import ru.hogwarts.scholl.dto.StudentDtoOut;
import ru.hogwarts.scholl.exception.FacultyNotFoundException;
import ru.hogwarts.scholl.exception.StudentNotFoundException;
import ru.hogwarts.scholl.mapper.FacultyMapper;
import ru.hogwarts.scholl.mapper.StudentMapper;
import ru.hogwarts.scholl.model.Student;
import ru.hogwarts.scholl.repository.FacultyRepository;
import ru.hogwarts.scholl.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentMapper studentMapper;
    private final FacultyMapper facultyMapper;

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper,
                          FacultyRepository facultyRepository,
                          FacultyMapper facultyMapper) {
        this.studentMapper = studentMapper;
        this.facultyMapper = facultyMapper;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public StudentDtoOut create(StudentDtoIn studentDtoIn) {
        return studentMapper.toDto(
                studentRepository.save(
                        studentMapper.toEntity(studentDtoIn)
                )
        );
    }

    public StudentDtoOut get(long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public StudentDtoOut update(long studentId, StudentDtoIn studentDtoIn) {
        Student updatedStudent = studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(studentDtoIn.getName());
                    student.setAge(studentDtoIn.getAge());
                    long facultyId = studentDtoIn.getFacultyId();
                    student.setFaculty(
                            facultyRepository.findById(facultyId)
                                    .orElseThrow(() -> new FacultyNotFoundException(facultyId))
                    );
                    return student;
                })
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        studentRepository.save(updatedStudent);
        return studentMapper.toDto(updatedStudent);
    }

    public StudentDtoOut delete(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.deleteById(id);
        return studentMapper.toDto(student);
    }

    public Collection<StudentDtoOut> findStudentsByAgeBetween(int from, int to) {
        return studentRepository.findStudentsByAgeBetween(from, to).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public Collection<StudentDtoOut> findAll(@Nullable Integer age) {
        return Optional.ofNullable(age)
                .map(studentRepository::findStudentsByAge)
                .orElseGet(studentRepository::findAll).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public FacultyDtoOut findStudentsFaculty(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }
}
