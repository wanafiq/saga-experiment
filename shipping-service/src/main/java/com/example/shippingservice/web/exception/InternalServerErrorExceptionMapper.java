package com.example.shippingservice.web.exception;

import com.example.shippingservice.web.response.ApiResponseBuilder;
import jakarta.annotation.Priority;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

    @Override
    public Response toResponse(InternalServerErrorException e) {
        return ApiResponseBuilder.buildWithErrors(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            null
        );
    }
}
