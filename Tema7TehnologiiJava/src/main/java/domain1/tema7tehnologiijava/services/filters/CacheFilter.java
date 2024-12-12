package domain1.tema7tehnologiijava.services.filters;

import jakarta.annotation.Priority;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
@Provider
@Priority(1)
@Singleton
public class CacheFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        if ("GET".equalsIgnoreCase(method)) {
            Object object = cache.get(path);
            if (object != null) {
                requestContext.abortWith(Response.ok(object).build());
            }
            return;
        }
        if ("POST".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            cache.clear();
        }
    }
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        if ("GET".equalsIgnoreCase(method) && responseContext.getStatus() == Response.Status.OK.getStatusCode()) {
            cache.put(path, responseContext.getEntity());
        }
    }
}
