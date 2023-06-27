package ru.hogwarts.scholl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.scholl.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(int from, int to);

    Collection<Student> findStudentsByAge(Integer age);

    Collection<Student> findAllByFaculty_id(long facultyId);
}
