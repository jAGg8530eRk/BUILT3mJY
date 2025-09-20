// 代码生成时间: 2025-09-20 11:02:53
import io.micronaut.context.annotation.Requires;
    import io.micronaut.http.HttpResponse;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.client.exceptions.HttpClientResponseException;
    import io.micronaut.http.client.RxHttpClient;
    import io.reactivex.Flowable;
    import io.reactivex.Single;
    import io.reactivex.schedulers.Schedulers;
    import javax.inject.Inject;
    import java.net.InetAddress;
    import java.net.UnknownHostException;
    import java.util.concurrent.ExecutionException;
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.TimeoutException;

    /**
     * NetworkConnectionChecker uses Micronaut to check network connectivity.
     */
    @Controller("/network")
    @Requires(property = "network.checker.enabled")
    public class NetworkConnectionChecker {

        @Inject
        RxHttpClient client;

        /**
         * Checks if a network connection is available by attempting to connect to a known host.
         *
         * @return A response indicating whether the network is reachable.
         */
        @Get("/ping")
        public HttpResponse<String> pingNetwork() {
            try {
                // Attempt to connect to Google's public DNS server as a known available host.
                InetAddress.getByName("8.8.8.8");
                // If successful, return a positive response.
                return HttpResponse.ok("Network is reachable.");
            } catch (UnknownHostException e) {
                // If connection fails, return an error response.
                return HttpResponse.status(503, "Network unreachable, check your connection.");
            }
        }

        /**
         * Checks network connection by performing a GET request to a server.
         *
         * @return A response indicating whether the server is reachable.
         */
        @Get("/check")
        public HttpResponse<String> checkServerConnection() {
            try {
                // Performs a GET request to a predefined URL.
                String url = "https://api.github.com"; // Example URL, can be replaced with any reliable API.
                Single<HttpResponse<String>> response = client.toBlocking().exchange(url, String.class);
                // If the server is reachable, return a positive response.
                return HttpResponse.ok("Server is reachable.");
            } catch (HttpClientResponseException e) {
                // If the server is not reachable or the request fails, return an error response.
                return HttpResponse.status(e.status().getCode(), "Server unreachable.");
            } catch (InterruptedException | ExecutionException e) {
                // Handle other exceptions that may occur during the request.
                return HttpResponse.status(500, "Error checking server connection.");
            }
        }
    }