package health;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Health {
    public static void main(String[] args) {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/actuator/health"))
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String body = response.body();

            if (!body.contains("UP")) {
                throw new RuntimeException("...");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}