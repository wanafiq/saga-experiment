package com.example.shippingservice.web.rest;

import com.example.shippingservice.service.ShippingService;
import com.example.shippingservice.web.response.ApiResponseBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/api/shipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class ShippingResource {

    @Inject
    ShippingService shippingService;

    @GET
    public Response getAll() {
        log.debug("REST request to get all shipments");

        var content = shippingService.getAll();

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        log.debug("REST request to get shipment: id={}", id);

        var content = shippingService.getById(id);

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }
}
