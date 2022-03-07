package com.sandbox.services.implementations.systemhealthcheck;

import com.sandbox.services.api.general.HealthChecker;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class SystemHealthCheckServiceTest {

    public static class HealthDummy implements HealthChecker {
        String errMessage = null;
        public HealthDummy() {

        }

        public HealthDummy(String errMessage) {
            this.errMessage = errMessage;
        }

        @Override
        public String errorMessages() {
            return errMessage;
        }
    }

    @Test
    public void whenHealthCheckServiceCalled_ensureAllHealthCheckersCalledAndAllSuccessfulReportsSuccessful() {
        SystemHealthCheckService service = new SystemHealthCheckService(new HealthDummy(), new HealthDummy());
        SystemHealthCheckResponse response = service.executeRequest(new SystemHealthCheckRequest());
        assertThat(response.isHealthy()).isTrue();
    }

    @Test
    public void whenHealthCheckServiceCalledAndOnlyOneFailsButOthersSucceed_expectUnhealthy() {
        SystemHealthCheckService service = new SystemHealthCheckService(new HealthDummy(), new HealthDummy(), new HealthDummy("Error!"), new HealthDummy(), new HealthDummy());
        SystemHealthCheckResponse response = service.executeRequest(new SystemHealthCheckRequest());
        assertThat(response.isHealthy()).isFalse();
        assertThat(response.getMessages()).hasSize(1);
        assertThat(response.getMessages().get(0)).isEqualTo("Error!");
    }
}
