package Gym.Management.mainRun;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class SMSNotification {

    public static void sendSMS(String phoneNumber, String message) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }

        if (message == null || message.isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }

        try {
            String url = "https://textbelt.com/text";
            String apiKey = "textbelt";  

            String requestBody = String.format("phone=%s&message=%s&key=%s",
                    URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8),
                    URLEncoder.encode(message, StandardCharsets.UTF_8),
                    apiKey);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    public static void main(String[] args) {
        // test and put ur number 
        sendSMS("+201234567890", "Hello, this is a test message from Textbelt!");  
    }
}
