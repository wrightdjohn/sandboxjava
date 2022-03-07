package com.sandbox.userinterface.controllers;

import com.sandbox.services.api.Service;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckRequest;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckResponse;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataRequest;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataResponse;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemController {
    protected Logger logger = LoggerFactory.getLogger(SystemController.class.getName());
    private Service<SystemHealthCheckRequest, SystemHealthCheckResponse> healthCheckService;
    private Service<SystemSetupDataRequest, SystemSetupDataResponse> setupDataService;

    public SystemController(Service healthCheckService, Service setupDataService) {
        this.healthCheckService = healthCheckService;
        this.setupDataService = setupDataService;
    }

    public Object healthCheck(spark.Request req, spark.Response res) {
        SystemHealthCheckRequest request = new SystemHealthCheckRequest();
        SystemHealthCheckResponse response = healthCheckService.executeRequest(request);
        if (response.isHealthy()) {
            res.status(200);
            return "success";
        }
        else {
            res.status(500);
            logger.error("Health Check Failed\n"+response.getMessages().stream().collect(Collectors.joining("\n")));
            return "unhealthy";
        }

    }

    public Object setupData(spark.Request req, spark.Response res) {
        SystemHealthCheckRequest request = new SystemHealthCheckRequest();
        SystemHealthCheckResponse response = healthCheckService.executeRequest(request);
        return "success";
    }
}
