package ru.hogwarts.scholl.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.scholl.dto.FacultyDtoOut;
import ru.hogwarts.scholl.dto.StudentDtoIn;
import ru.hogwarts.scholl.dto.StudentDtoOut;
import ru.hogwarts.scholl.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public StudentDtoOut create(@RequestBody StudentDtoIn studentDtoIn) {
        return service.create(studentDtoIn);
    }

    @GetMapping("/{id}")
    public StudentDtoOut get(@PathVariable long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public StudentDtoOut update(@PathVariable long id,
                                @RequestBody StudentDtoIn studentDtoIn) {
        return service.update(id, studentDtoIn);
    }

    @DeleteMapping("/{id}")
    public StudentDtoOut delete(@PathVariable long id) {
        return service.delete(id);
    }

    @GetMapping("/{age}/students")
    public Collection<StudentDtoOut> findAll(@PathVariable(required = false) int age) {
        return service.findAll(age);
    }

    @GetMapping("/filter")
    public Collection<StudentDtoOut> findStudentsByAgeBetween(@RequestParam int from,
                                                              @RequestParam int to) {
        return service.findStudentsByAgeBetween(from, to);
    }

    @GetMapping("/{id}/faculty")
    public FacultyDtoOut findStudentsFaculty(@PathVariable Long id) {
        return service.findStudentsFaculty(id);
    }
}
