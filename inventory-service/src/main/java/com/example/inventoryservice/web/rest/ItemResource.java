package com.example.inventoryservice.web.rest;

import com.example.inventoryservice.service.ItemService;
import com.example.inventoryservice.web.response.ApiResponseBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/api/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class ItemResource {

    @Inject
    ItemService itemService;

    @GET
    public Response getAll() {
        log.debug("REST request to get all items");

        var content = itemService.getAll();

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        log.debug("REST request to get item: id={}", id);

        var content = itemService.getById(id);

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }
}
