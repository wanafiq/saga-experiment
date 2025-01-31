package com.example.inventoryservice.web.exception;

import com.example.inventoryservice.web.response.ApiResponseBuilder;
import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
@Priority(1)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();

        return ApiResponseBuilder.buildWithErrors(Response.Status.BAD_REQUEST.getStatusCode(), "ConstraintViolation", errors);
    }
}
