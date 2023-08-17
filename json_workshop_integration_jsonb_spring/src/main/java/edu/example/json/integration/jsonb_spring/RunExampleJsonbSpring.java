package edu.example.json.integration.jsonb_spring;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class RunExampleJsonbSpring {
    private static final String SAMPLE_URI = "http://localhost:8080/rest";
    private static final String SAMPLE_BOOK = """
        {   "pos": 12,
            "newListing": false,
            "weeks": 13,
            "title": "Zieht euch warm an, es wird noch heißer!",
            "authors": [
                {
                    "lastname": "Plöger",
                    "firstname": "Sven"
                }
            ],
            "publisher": "Westend",
            "price": 22.00
        }
        """;

    private void runWithObjectBody() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(SAMPLE_URI+"/process-mapping"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(SAMPLE_BOOK))
            .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void runWithStringBody() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(SAMPLE_URI+"/process-string"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(SAMPLE_BOOK))
            .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        RunExampleJsonbSpring app = new RunExampleJsonbSpring();
        app.runWithObjectBody();
        app.runWithStringBody();
    }
}

