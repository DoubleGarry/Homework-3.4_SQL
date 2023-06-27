package ru.hogwarts.scholl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.scholl.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(String color, String name);


    Optional<Faculty> findByNameIgnoreCase(String facultyName);
}
