package domain1.tema7tehnologiijava.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@Named("microserviceBean")
@SessionScoped
public class MicroserviceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void startMicroservice() {
        try {
            // Comanda pentru a porni microserviciul JAR generat
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", "target/demo-microbundle.jar");
            builder.directory(new File("/home/dan/Documents/TehnologiiJavaTeme/Tema7TehnologiiJava/demo"));
            builder.inheritIO();  // Permite redirectarea output-ului către consola curentă
            Process process = builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
