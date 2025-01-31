package com.example.paymentservice.web.rest;

import com.example.paymentservice.service.TransactionService;
import com.example.paymentservice.web.response.ApiResponseBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/api/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @GET
    public Response getAll() {
        log.debug("REST request to get all shipments");

        var content = transactionService.getAll();

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        log.debug("REST request to get shipment: id={}", id);

        var content = transactionService.getById(id);

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }
}
