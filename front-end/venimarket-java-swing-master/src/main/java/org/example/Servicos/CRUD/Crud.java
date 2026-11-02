package org.example.Servicos.CRUD;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Crud {
    private String endpoint;

    private String url = "http://localhost:8080/";

    public Crud(String endpoint){
        this.endpoint = endpoint;
    }
    public Crud(){

    }
    public HttpResponse returnResponseGET() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
    public HttpResponse<String> patch(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody == null ? "" : jsonBody))
                .header("Content-Type", "application/json")
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public  HttpResponse<String> post(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
    public HttpResponse<String> put(String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+endpoint))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public List<Map> getAll() throws IOException, InterruptedException {
        HttpResponse<String> response = returnResponseGET();
        String body = response.body();
        if (response.statusCode() != 200 || body == null || !body.trim().startsWith("[")) {
            System.out.println("GET " + endpoint + " -> " + response.statusCode() + " " + body);
            return new ArrayList<>();
        }
        return new ObjectMapper().readValue(body, new TypeReference<List<Map>>() {});
    }

    public Map getBy() throws IOException, InterruptedException {
        HttpResponse<String> response = returnResponseGET();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<Map>() {});
    }
}