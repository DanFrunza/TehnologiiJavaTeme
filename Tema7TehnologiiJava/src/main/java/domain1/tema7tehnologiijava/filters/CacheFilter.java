package domain1.tema7tehnologiijava.filters;

import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider
@PreMatching
public class CacheFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static Map<String, Response> cache = new HashMap<>();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if ("GET".equals(requestContext.getMethod())) {
            String path = requestContext.getUriInfo().getPath();
            if (cache.containsKey(path)) {
                requestContext.abortWith(cache.get(path));
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if ("GET".equals(requestContext.getMethod()) && responseContext.getStatus() == 200) {
            String path = requestContext.getUriInfo().getPath();

            // Construiește un ResponseBuilder
            Response.ResponseBuilder responseBuilder = Response.status(responseContext.getStatus())
                    .entity(responseContext.getEntity()); // Setează entitatea răspunsului

            // Adaugă headerele la ResponseBuilder
            for (Map.Entry<String, List<Object>> header : responseContext.getHeaders().entrySet()) {
                for (Object value : header.getValue()) {
                    responseBuilder.header(header.getKey(), value);
                }
            }

            // Construiește răspunsul final și adaugă-l în cache
            Response cachedResponse = responseBuilder.build();
            cache.put(path, cachedResponse);
        }
    }

    public static void resetCache() {
        cache.clear();
    }
}
