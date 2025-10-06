// 代码生成时间: 2025-10-06 20:58:44
package com.example.monitor;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.inject.Singleton;

@Factory
public class NetworkLatencyMonitor {

    // Configuration properties
    private static final String HOST = "google.com";
    private static final int MAX_ATTEMPTS = 3;
    private static final long TIMEOUT_MS = 5000; // 5 seconds

    // Singleton instance of NetworkLatencyMonitor
    @Bean
    @Singleton
    public static NetworkLatencyMonitor create() {
        return new NetworkLatencyMonitor();
    }

    // Method to ping the host and calculate latency
    public double pingHost() {
        int latencySum = 0;
        int successfulPings = 0;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                long startTime = System.nanoTime();
                InetAddress address = InetAddress.getByName(HOST);
                boolean pingSuccess = address.isReachable(TIMEOUT_MS);
                long endTime = System.nanoTime();

                if (pingSuccess) {
                    long latency = (endTime - startTime) / 1000000; // Convert to milliseconds
                    latencySum += latency;
                    successfulPings++;
                }
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + HOST);
                break;
            } catch (Exception e) {
                System.err.println("Error pinging host: " + e.getMessage());
            }
        }

        if (successfulPings > 0) {
            return (double) latencySum / successfulPings;
        } else {
            throw new IllegalStateException("No successful pings to calculate latency");
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        NetworkLatencyMonitor monitor = create();
        try {
            double latency = monitor.pingHost();
            System.out.println("Average network latency to " + HOST + ": " + latency + " ms");
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
