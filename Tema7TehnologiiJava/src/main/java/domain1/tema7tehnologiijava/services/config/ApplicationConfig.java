package domain1.tema7tehnologiijava.services.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
@ApplicationScoped
public class ApplicationConfig extends Application {
}
