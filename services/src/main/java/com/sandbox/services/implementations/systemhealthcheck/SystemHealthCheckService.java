package com.sandbox.services.implementations.systemhealthcheck;

import com.sandbox.services.api.Service;
import com.sandbox.services.api.general.HealthChecker;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SystemHealthCheckService implements Service<SystemHealthCheckRequest,SystemHealthCheckResponse> {
    List<HealthChecker> healthCheckers;

    public SystemHealthCheckService(List<HealthChecker> healthCheckers) {
        this.healthCheckers = healthCheckers;
    }

    public SystemHealthCheckService(HealthChecker...healthCheckers) {
        this.healthCheckers = Arrays.asList(healthCheckers);
    }


    @Override
    public SystemHealthCheckResponse executeRequest(SystemHealthCheckRequest request) {
        List<String> errorMessages =
                healthCheckers.stream().map(HealthChecker::errorMessages).filter(Objects::nonNull).toList();

        boolean allHealthy = errorMessages.size() == 0;

        return new SystemHealthCheckResponse(allHealthy,errorMessages);
    }
}
