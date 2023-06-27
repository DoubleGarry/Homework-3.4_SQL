package ru.hogwarts.scholl.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.scholl.dto.StudentDtoIn;
import ru.hogwarts.scholl.dto.StudentDtoOut;
import ru.hogwarts.scholl.exception.FacultyNotFoundException;
import ru.hogwarts.scholl.model.Student;
import ru.hogwarts.scholl.repository.FacultyRepository;

import java.util.Optional;

@Component
public class StudentMapper {
    private final FacultyMapper facultyMapper;
    private final FacultyRepository facultyRepository;

    public StudentMapper(FacultyMapper facultyMapper, FacultyRepository facultyRepository) {
        this.facultyMapper = facultyMapper;
        this.facultyRepository = facultyRepository;
    }

    public StudentDtoOut toDto(Student student) {
        StudentDtoOut studentDtoOut = new StudentDtoOut();
        studentDtoOut.setId(student.getId());
        studentDtoOut.setName(student.getName());
        studentDtoOut.setAge(student.getAge());
        Optional.ofNullable(student.getFaculty())
                .ifPresent(faculty -> studentDtoOut.setFaculty(facultyMapper.toDto(faculty)));
        return studentDtoOut;
    }

    public Student toEntity(StudentDtoIn studentDtoIn) {
        Student student = new Student();
        student.setName(studentDtoIn.getName());
        student.setAge(studentDtoIn.getAge());
        student.setFaculty(
                facultyRepository.findById(
                        studentDtoIn.getFacultyId()
                ).orElseThrow(() -> new FacultyNotFoundException(studentDtoIn.getFacultyId()))
        );
        return student;
    }
}
