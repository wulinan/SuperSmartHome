package com.tos.module.apiprovider;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class.
 *
 */
public class ApiProviderMain {
    // Base URI the Grizzly HTTP server will listen on
    public static final URI BASE_URI = URI.create("http://localhost:8080/myapp/");
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
  
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

    
    try {
        System.out.println("JSON with JAXB Jersey Example App");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp(), false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
            }
        }));
        server.start();

        System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

        Thread.currentThread().join();
    } catch (IOException | InterruptedException ex) {
        Logger.getLogger(ApiProviderMain.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public static ResourceConfig createApp() {
    return new ResourceConfig()
            .register(new JettisonFeature())
            .packages("com.tos.module.apiprovider");
}
}

