package com.example.orchestratorservice.web.exception;

import com.example.orchestratorservice.web.response.ApiResponseBuilder;
import jakarta.annotation.Priority;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Priority(1)
@Slf4j
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

    @Override
    public Response toResponse(InternalServerErrorException e) {
        log.error("Internal server error: {}", e.getMessage());
        return ApiResponseBuilder.buildWithErrors(Response.Status.INTERNAL_SERVER_ERROR);
    }
}
