package com.ingemark.springdemo.controller;

import com.ingemark.springdemo.mapper.InstructorMapper;
import com.ingemark.springdemo.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public InstructorResponse createInstructor(@Valid @RequestBody InstructorRequest instructorRequest) {
        var instructor = instructorService.save(InstructorMapper.toInstructor(instructorRequest));
        return InstructorMapper.toInstructorResponse(instructor);
    }

    @PutMapping
    public InstructorResponse updateInstructor(@Valid @RequestBody InstructorRequest instructorRequest) {
        var instructor = instructorService.update(InstructorMapper.toInstructor(instructorRequest));
        return InstructorMapper.toInstructorResponse(instructor);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstructorById(@PathVariable Long id) {
        instructorService.deleteById(id);
    }
}
