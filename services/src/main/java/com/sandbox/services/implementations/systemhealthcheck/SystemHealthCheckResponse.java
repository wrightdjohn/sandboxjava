package com.sandbox.services.implementations.systemhealthcheck;

import com.sandbox.services.api.ServiceResponse;
import java.util.List;

public class SystemHealthCheckResponse implements ServiceResponse {
    boolean healthy;
    private List<String> messages;

    public SystemHealthCheckResponse(boolean healthy) {
        this.healthy = healthy;
    }

    public SystemHealthCheckResponse(boolean healthy, List<String> messages) {
        this.healthy = healthy;
        this.messages = messages;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public List<String> getMessages() {
        return messages;
    }
}
