package com.modasby;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class RequestInterceptor implements ContainerRequestFilter {

    private final Set<String> allowedPaths;
    private final AuthenticationService authenticationService;

    @Inject
    public RequestInterceptor(AuthenticationService authenticationService) {
        Set<String> allowedPaths = new HashSet<>();

        allowedPaths.add("/signup");

        this.allowedPaths = allowedPaths;
        this.authenticationService = authenticationService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        if (isPathAllowed(path)) return;

        List<String> authorizationHeaders = requestContext.getHeaders().get("Authorization");
        if (authorizationHeaders == null || authorizationHeaders.isEmpty() || !isAuthenticated(authorizationHeaders.getFirst())) {
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build()
        );
    }

    private boolean isPathAllowed(String path) {
        return this.allowedPaths.contains(path);
    }

    private boolean isAuthenticated(String token) {
        return authenticationService.authenticate(token);
    }
}
