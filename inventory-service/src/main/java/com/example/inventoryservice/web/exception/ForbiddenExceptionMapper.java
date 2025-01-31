package com.example.inventoryservice.web.exception;

import com.example.inventoryservice.web.response.ApiResponseBuilder;
import io.quarkus.security.ForbiddenException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException e) {
        return ApiResponseBuilder.buildWithErrors(
            Response.Status.FORBIDDEN.getStatusCode(),
            Response.Status.FORBIDDEN.getReasonPhrase(),
            null
        );
    }
}
