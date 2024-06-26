package com.modasby;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ExampleResource {
    @Inject
    AuthenticationService service;

    @GET
    @Path("/foo-bar")
    public Response foobar() {
        return Response.noContent().build();
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createAccount(CredentialsDto credentialsDto) {
        return service.addUser(credentialsDto);
    }
}
