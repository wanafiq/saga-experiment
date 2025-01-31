package com.example.orchestratorservice.web.rest;

import com.example.orchestratorservice.service.dto.BuyItemDTO;
import com.example.orchestratorservice.service.saga.SagaBuyItemService;
import com.example.orchestratorservice.web.response.ApiResponseBuilder;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/api/items")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    SagaBuyItemService sagaBuyItemService;

    @POST
    public Response create(@Valid BuyItemDTO dto) {
        log.debug("REST request to buy item");
        log.debug("dto={}", dto);

        var content = sagaBuyItemService.execute(dto);

        return ApiResponseBuilder.build(Response.Status.OK, content);
    }
}
