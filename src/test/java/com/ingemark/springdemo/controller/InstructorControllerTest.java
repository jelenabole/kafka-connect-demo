package com.ingemark.springdemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ingemark.springdemo.TestBase;
import com.ingemark.springdemo.controller.advice.ErrorDetail;
import com.ingemark.springdemo.fixture.InstructorFixture;
import com.ingemark.springdemo.model.Instructor;
import com.ingemark.springdemo.repository.InstructorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.ingemark.springdemo.controller.advice.ErrorDetail.ErrorCode.FIELD_VALIDATION_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InstructorControllerTest extends TestBase {

    private static final String INSTRUCTOR_CONTROLLER_API = "/api/instructors";

    @Autowired
    private InstructorRepository instructorRepository;

    @Test
    @DisplayName("Save-instructor should create new instructor.")
    void testSaveInstructor() throws Exception {
        // when
        var preparedInstructor = InstructorFixture.instructorRequestBuilder().build();

        var response = mockMvc.perform(post(INSTRUCTOR_CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preparedInstructor)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        var instructor = objectMapper.readValue(response, InstructorResponse.class);
        assertThat(instructor.id()).isEqualTo(1L);
        assertThat(instructor.firstName()).isEqualTo(preparedInstructor.firstName());
        assertThat(instructor.lastName()).isEqualTo(preparedInstructor.lastName());
        assertThat(instructor.email()).isEqualTo(preparedInstructor.email());
        assertThat(instructor.salary()).isEqualTo(preparedInstructor.salary());

        var instructorListFromDb = instructorRepository.findAll();
        assertThat(instructorListFromDb).hasSize(1);

        var instructorFromDb = instructorRepository.findById(instructor.id()).orElseThrow();
        assertThat(instructorFromDb.getId()).isEqualTo(1L);
        assertThat(instructorFromDb.getFirstName()).isEqualTo(preparedInstructor.firstName());
        assertThat(instructorFromDb.getLastName()).isEqualTo(preparedInstructor.lastName());
        assertThat(instructorFromDb.getEmail()).isEqualTo(preparedInstructor.email());
        assertThat(instructorFromDb.getSalary()).isEqualTo(preparedInstructor.salary());
        assertThat(instructorFromDb.getModifiedAt()).isNull();
        assertThat(instructorFromDb.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Save-instructor should return BAD_REQUEST if some field is invalid.")
    void testSaveInstructorWithInvalidData() throws Exception {
        // when
        var preparedInstructor = InstructorFixture.instructorRequestBuilder()
                .withFirstName("")
                .withLastName("")
                .withSalary(BigDecimal.valueOf(-1))
                .withEmail("randomString")
                .build();

        var response = mockMvc.perform(post(INSTRUCTOR_CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preparedInstructor)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        // then
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);
        assertThat(problemDetail).isNotNull();
        assertThat(problemDetail.getProperties()).isNotNull();
        assertThat(problemDetail.getProperties().get("errors")).isNotNull();

        var errors = objectMapper.convertValue(problemDetail.getProperties().get("errors"),
                new TypeReference<List<ErrorDetail>>() {});

        assertThat(errors).hasSize(4)
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "firstName") &&
                        Objects.equals(error.getErrorMessage(), "must not be blank") &&
                        Objects.equals(error.getFieldValue(), ""))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "lastName") &&
                        Objects.equals(error.getErrorMessage(), "must not be blank") &&
                        Objects.equals(error.getFieldValue(), ""))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "salary") &&
                        Objects.equals(error.getErrorMessage(), "must be greater than or equal to 0") &&
                        Objects.equals(error.getFieldValue(), "-1"))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "email") &&
                        Objects.equals(error.getErrorMessage(), "must be a well-formed email address") &&
                        Objects.equals(error.getFieldValue(), "randomString"));
    }

    @Test
    @DisplayName("Update-instructor should update existing instructor's info without changing its courses.")
    void testUpdateInstructor() throws Exception {
        // given
        var savedInstructor = saveInstructorWithCourse();

        // when
        var updatedInstructor = InstructorFixture.instructorRequestBuilder().withId(savedInstructor.getId()).build();

        var response = mockMvc.perform(put(INSTRUCTOR_CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInstructor)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        var instructor = objectMapper.readValue(response, InstructorResponse.class);
        assertThat(instructor.id()).isEqualTo(updatedInstructor.id());
        assertThat(instructor.firstName()).isEqualTo(updatedInstructor.firstName());
        assertThat(instructor.lastName()).isEqualTo(updatedInstructor.lastName());
        assertThat(instructor.email()).isEqualTo(updatedInstructor.email());
        assertThat(instructor.salary()).isEqualTo(updatedInstructor.salary());

        var instructorListFromDb = instructorRepository.findAll();
        assertThat(instructorListFromDb).hasSize(1);

        var instructorFromDb = instructorRepository.findById(savedInstructor.getId()).orElseThrow();
        assertThat(instructorFromDb.getId()).isEqualTo(updatedInstructor.id());
        assertThat(instructorFromDb.getFirstName()).isEqualTo(updatedInstructor.firstName());
        assertThat(instructorFromDb.getLastName()).isEqualTo(updatedInstructor.lastName());
        assertThat(instructorFromDb.getEmail()).isEqualTo(updatedInstructor.email());
        assertThat(instructorFromDb.getSalary()).isEqualTo(updatedInstructor.salary());
        assertThat(instructorFromDb.getModifiedAt()).isNotNull();
        assertThat(instructorFromDb.getCreatedAt()).isNotNull().isBefore(instructorFromDb.getModifiedAt());
    }

    @Test
    @DisplayName("Update-instructor should return BAD_REQUEST if some field is invalid.")
    void testUpdateInstructorWithInvalidData() throws Exception {
        // given
        var savedInstructor = saveInstructorWithCourse();

        // when
        var updatedInstructor = InstructorFixture.instructorRequestBuilder().withId(savedInstructor.getId())
                .withFirstName(null)
                .withLastName(null)
                .withSalary(null)
                .withEmail(null)
                .build();

        var response = mockMvc.perform(put(INSTRUCTOR_CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInstructor)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        //then
        var problemDetail = objectMapper.readValue(response, ProblemDetail.class);
        assertThat(problemDetail).isNotNull();
        assertThat(problemDetail.getProperties()).isNotNull();
        assertThat(problemDetail.getProperties().get("errors")).isNotNull();

        var errors = objectMapper.convertValue(problemDetail.getProperties().get("errors"),
                new TypeReference<List<ErrorDetail>>() {});

        assertThat(errors).hasSize(4)
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "firstName") &&
                        Objects.equals(error.getErrorMessage(), "must not be blank") &&
                        Objects.equals(error.getFieldValue(), "null"))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "lastName") &&
                        Objects.equals(error.getErrorMessage(), "must not be blank") &&
                        Objects.equals(error.getFieldValue(), "null"))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "salary") &&
                        Objects.equals(error.getErrorMessage(), "must not be null") &&
                        Objects.equals(error.getFieldValue(), "null"))
                .anyMatch(error -> Objects.equals(error.getErrorCode(), String.valueOf(FIELD_VALIDATION_ERROR)) &&
                        Objects.equals(error.getFieldName(), "email") &&
                        Objects.equals(error.getErrorMessage(), "must not be blank") &&
                        Objects.equals(error.getFieldValue(), "null"));
    }

    @Test
    @DisplayName("Delete-instructor should delete instructor with the given id, if it exists, and remove him from all his courses.")
    void testDeleteById() throws Exception {
        // given
        var instructor = saveInstructorWithCourse();
        var additionalInstructor = instructorRepository.save(InstructorFixture.instructorBuilder().withEmail("test@email.com").build());

        // when
        mockMvc.perform(delete(INSTRUCTOR_CONTROLLER_API + "/" + instructor.getId()))
                .andExpect(status().isNoContent());

        // then
        var instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(1);
        assertThat(instructors.get(0).getId()).isEqualTo(additionalInstructor.getId());

        assertThat(instructorRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Delete-instructor should return NO_CONTENT status even if the given id does not exist.")
    void testDeleteById_nonExistingId() throws Exception {
        // given
        saveInstructorWithCourse();

        // when
        var nonExistingId = 3;
        mockMvc.perform(delete(INSTRUCTOR_CONTROLLER_API + "/" + nonExistingId))
                .andExpect(status().isNoContent());

        // then
        assertThat(instructorRepository.findAll()).hasSize(1);
    }

    private Instructor saveInstructorWithCourse() {
        return instructorRepository.save(InstructorFixture.instructorBuilder().build());
    }

}
