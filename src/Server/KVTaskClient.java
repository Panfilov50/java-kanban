package Server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String apiToken;
    private final HttpClient taskClient;
    private final String uriServer;

    public KVTaskClient(String str) throws IOException, InterruptedException {
        uriServer = str;
        taskClient = HttpClient.newHttpClient();
        URI uri = URI.create(uriServer + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = taskClient.send(request, handler);
        apiToken = response.body();
    }

    public void put(String key, String json) {
        try {
            URI uri = URI.create(uriServer + "/save/" + key + "?API_TOKEN=" + apiToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Код ответа: " + response.statusCode());
        } catch (InterruptedException | IOException ignored){
            System.out.println("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(uriServer + "/load/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response =  taskClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Код ответа: " + response.statusCode());
        return response.body();
    }

}