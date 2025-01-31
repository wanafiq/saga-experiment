package com.example.shippingservice.web.exception;

import com.example.shippingservice.web.response.ApiResponseBuilder;
import jakarta.annotation.Priority;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return ApiResponseBuilder.buildWithErrors(
            Response.Status.NOT_FOUND.getStatusCode(),
            Response.Status.NOT_FOUND.getReasonPhrase(),
            null
        );
    }
}
