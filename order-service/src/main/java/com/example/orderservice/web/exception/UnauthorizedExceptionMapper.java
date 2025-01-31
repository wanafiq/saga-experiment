package com.example.orderservice.web.exception;

import com.example.orderservice.web.response.ApiResponseBuilder;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException e) {
        return ApiResponseBuilder.buildWithErrors(
            Response.Status.UNAUTHORIZED.getStatusCode(),
            Response.Status.UNAUTHORIZED.getReasonPhrase(),
            null
        );
    }
}
