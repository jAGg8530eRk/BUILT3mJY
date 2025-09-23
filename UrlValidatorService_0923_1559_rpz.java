// 代码生成时间: 2025-09-23 15:59:29
package com.example.micronaut.urlvalidator;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.client.annotation.Client;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Singleton;

@Requires(env = "default")
@Factory
public class UrlValidatorService {

    @Bean
    @Singleton
    public HttpClient httpClient() {
        return HttpClient.builder()
                .baseUrl("https://www.example.com/")
                .build();
    }

    @Client("/")
    private HttpClient httpClient;

    public boolean validateUrl(String url) {
        // Validate if the URL is null or empty
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            // Create a URI object from the URL string
            URI uri = new URI(url);
            // Check if the scheme is either http or https
            if (!(uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
                return false;
            }
        } catch (URISyntaxException e) {
            // If an exception occurs, the URL is invalid
            return false;
        }

        try {
            // Make a HEAD request to check if the URL is reachable
            HttpResponse<String> response = httpClient.toBlocking().exchange(url, String.class);
            // Check if the response status is OK (200)
            return response.getStatus() == HttpStatus.OK;
        } catch (HttpClientResponseException e) {
            // If the response status is not OK, the URL is not valid
            return false;
        } catch (Exception e) {
            // If any other exception occurs, log and return false
            e.printStackTrace();
            return false;
        }
    }
}
