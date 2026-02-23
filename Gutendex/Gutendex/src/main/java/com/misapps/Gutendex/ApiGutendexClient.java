package com.misapps.Gutendex;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ApiGutendexClient {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public String buscarLibroPorTitulo(String titulo) {

        String direccion = URL_BASE + titulo.replace(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar API");
        }
    }
}
