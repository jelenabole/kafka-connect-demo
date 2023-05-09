package com.ingemark.springdemo.repository;

import com.ingemark.springdemo.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Instructor i WHERE i.id = ?1")
    void deleteById(Long id);

}
