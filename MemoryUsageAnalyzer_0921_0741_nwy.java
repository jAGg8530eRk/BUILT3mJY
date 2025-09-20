// 代码生成时间: 2025-09-21 07:41:10
package com.example.memoryanalyzer;

import io.micronaut.context.annotation.Requires;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.micronaut.management.health.indicator.Indicator;
import javax.inject.Singleton;

@Requires(env = "prod") // This component is only required in a production environment
@Singleton
public class MemoryUsageAnalyzer implements HealthIndicator, Indicator {

    private static final String NAME = "memory";

    @Override
    public HealthResult check() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();

        double memoryUsagePercentage = (double) usedMemory / maxMemory * 100;

        return HealthResult.builder(NAME)
                .details(Map.of(
                        "usedMemory", usedMemory,
                        "maxMemory", maxMemory,
                        "freeMemory", freeMemory,
                        "memoryUsagePercentage", memoryUsagePercentage
                ))
                .status(getStatus(memoryUsagePercentage))
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }
# 扩展功能模块

    private HealthResult.Status getStatus(double memoryUsagePercentage) {
        if (memoryUsagePercentage > 85) {
            return HealthResult.Status.DOWN;
        } else if (memoryUsagePercentage > 75) {
            return HealthResult.Status.OUT_OF_SERVICE;
        } else {
            return HealthResult.Status.UP;
        }
    }
}
