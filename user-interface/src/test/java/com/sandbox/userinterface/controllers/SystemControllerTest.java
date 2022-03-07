package com.sandbox.userinterface.controllers;

import com.sandbox.allsystemutils.json.JsonMapper;
import com.sandbox.services.api.Service;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckRequest;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckResponse;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckService;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataRequest;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataResponse;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataService;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import spark.Request;
import spark.Response;

public class SystemControllerTest {

    Request req;
    Response res;
    private Service<SystemHealthCheckRequest, SystemHealthCheckResponse> healthCheckService;
    private Service<SystemSetupDataRequest, SystemSetupDataResponse> setupDataService;
    private SystemController systemController;
    private JsonMapper jsonMapper = new JsonMapper();

    @BeforeEach
    public void setup() {
        req = Mockito.mock(Request.class);
        res = Mockito.mock(Response.class);
        healthCheckService = Mockito.mock(SystemHealthCheckService.class);
        setupDataService = Mockito.mock(SystemSetupDataService.class);
        systemController = new SystemController(healthCheckService, setupDataService);

    }

    @Test
    public void whenHealthy_expectSuccessAnd200status() {
        SystemHealthCheckResponse response = new SystemHealthCheckResponse(true);
        Mockito.when(healthCheckService.executeRequest(any(SystemHealthCheckRequest.class))).thenReturn(response);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(res).status(argumentCaptor.capture());
        String result = (String) systemController.healthCheck(req,res);
        assertThat(result).isEqualTo("success");
        assertThat(argumentCaptor.getValue()).isEqualTo(200);
    }

    @Test
    public void whenUnHealthly_expectUnhealthyAnd500Status() {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("error #1");
        SystemHealthCheckResponse response = new SystemHealthCheckResponse(false, errorMessages);
        Mockito.when(healthCheckService.executeRequest(any(SystemHealthCheckRequest.class))).thenReturn(response);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(res).status(argumentCaptor.capture());
        String result = (String) systemController.healthCheck(req,res);
        assertThat(result).isEqualTo("unhealthy");
        assertThat(argumentCaptor.getValue()).isEqualTo(500);
    }

    @Test
    public void whenSettingUpDate_expectSuccess() {
        String result = (String) systemController.setupData(req,res);
        assertThat(result).isEqualTo("success");
    }
}
