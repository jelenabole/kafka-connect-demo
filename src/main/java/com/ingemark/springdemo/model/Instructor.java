package com.ingemark.springdemo.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor // needed for JPA
@Entity
public class Instructor extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;

}
