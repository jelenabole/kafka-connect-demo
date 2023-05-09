package com.ingemark.springdemo.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor // Jackson
@ToString
@Getter
public class ErrorDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = -4773274133144096117L;

    private String fieldName;
    private String errorCode;
    private String errorMessage;
    private String fieldValue;

    public ErrorDetail(FieldError fieldError) {
        this.fieldName = fieldError.getField();
        this.errorCode = String.valueOf(ErrorCode.FIELD_VALIDATION_ERROR);
        this.errorMessage = fieldError.getDefaultMessage();
        this.fieldValue = Objects.toString(fieldError.getRejectedValue());
    }

    public enum ErrorCode {
        FIELD_VALIDATION_ERROR, RESOURCE_NOT_FOUND_ERROR
    }
}
