// 代码生成时间: 2025-10-08 02:09:19
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import java.util.Random;

public class ProgramaticGenerationMicronaut {

    // Main method to start the Micronaut server
    public static void main(String[] args) {
        try (EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, args)) {
            Random random = new Random();
            // Port number can be configured here or taken from the environment
            int port = server.getPort();
            String url = "http://localhost:" + port + "/generate";
            HttpClient client = server.getHttpClient();

            // Simulate data generation and sending requests
            for (int i = 0; i < 5; i++) {
                String generatedData = generateRandomData(random);
                HttpRequest request = HttpRequest.POST(url, generatedData);
                // Send the request and handle the response
                String response = client.toBlocking().exchange(request, String.class).getBody().orElse("No response");
                System.out.println("Generated and sent data: " + generatedData);
                System.out.println("Server response: " + response);
            }
        }
    }

    // Method to generate random data
    private static String generateRandomData(Random random) {
        // This is a simple example generating a random number
        return "Data_" + random.nextInt(10000);
    }
}
