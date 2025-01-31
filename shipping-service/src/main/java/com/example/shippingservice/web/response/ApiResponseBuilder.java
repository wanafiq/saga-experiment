package com.example.shippingservice.web.response;

import jakarta.ws.rs.core.Response;
import java.util.List;

public class ApiResponseBuilder {

    public static <T> Response build(T content) {
        var response = new ApiResponse<>(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), content);

        return Response.status(Response.Status.OK.getStatusCode()).entity(response).build();
    }

    public static <T> Response build(Response.Status status, T content) {
        var response = new ApiResponse<>(status.getStatusCode(), status.getReasonPhrase(), content);

        return Response.status(status).entity(response).build();
    }

    public static <T> Response build(int status, String message, T content) {
        var response = new ApiResponse<>(status, message, content);

        return Response.status(status).entity(response).build();
    }

    public static <T> Response buildWithErrors(Response.Status status) {
        var response = new ApiResponse<>(status.getStatusCode(), status.getReasonPhrase(), null);

        return Response.status(status).entity(response).build();
    }

    public static <T> Response buildWithErrors(int status, String message, List<String> errors) {
        var response = new ApiResponse<>(status, message, errors);

        return Response.status(status).entity(response).build();
    }

    public static <T> Response buildWithPagination(ApiPaginationResponse<T> response) {
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
