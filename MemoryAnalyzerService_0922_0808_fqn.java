// 代码生成时间: 2025-09-22 08:08:37
package com.example.memory;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Introspected;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import javax.inject.Singleton;

@Singleton
@Introspected
public class MemoryAnalyzerService {
    // Injected configuration property
    @Value('${memory.threshold:50}')
    private int memoryThreshold;

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    /**
     * Returns the current memory usage statistics.
     *
     * @return MemoryUsageStatistics containing heap and non-heap usage.
     */
    public MemoryUsageStatistics getMemoryUsage() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
            return new MemoryUsageStatistics(
                    heapMemoryUsage.getUsed(),
                    heapMemoryUsage.getMax(),
                    nonHeapMemoryUsage.getUsed(),
                    nonHeapMemoryUsage.getMax(),
                    memoryThreshold
            );
        } catch (Exception e) {
            // Handle exceptions and log them accordingly
            throw new RuntimeException("Error retrieving memory usage", e);
        }
    }

    /**
     * Represents memory usage statistics.
     */
    public static class MemoryUsageStatistics {
        private final long heapUsed;
        private final long heapMax;
        private final long nonHeapUsed;
        private final long nonHeapMax;
        private final int memoryThreshold;

        public MemoryUsageStatistics(
                long heapUsed,
                long heapMax,
                long nonHeapUsed,
                long nonHeapMax,
                int memoryThreshold) {
            this.heapUsed = heapUsed;
            this.heapMax = heapMax;
            this.nonHeapUsed = nonHeapUsed;
            this.nonHeapMax = nonHeapMax;
            this.memoryThreshold = memoryThreshold;
        }

        // Getters for memory usage statistics
        public long getHeapUsed() { return heapUsed; }
        public long getHeapMax() { return heapMax; }
        public long getNonHeapUsed() { return nonHeapUsed; }
        public long getNonHeapMax() { return nonHeapMax; }
        public int getMemoryThreshold() { return memoryThreshold; }
    }
}
