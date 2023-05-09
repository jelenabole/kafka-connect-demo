package com.ingemark.springdemo.service;

import com.ingemark.springdemo.model.Instructor;
import com.ingemark.springdemo.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Page<Instructor> getAllPaginated(Pageable pageable) {
        return instructorRepository.findAll(pageable);
    }

    public Instructor getById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
    }

    @Transactional
    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Instructor update(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Transactional
    public void deleteById(Long id) {
        instructorRepository.deleteById(id);
    }
}
