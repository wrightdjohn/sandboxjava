package com.sandbox.services.implementations.systemsetupdata;

import com.sandbox.services.api.dao.SetupData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SystemSetupDataTest {

    @Test
    public void whenSetupDataIsCalled_expectSetupDataToBeCalled() {
        SetupData setupData = Mockito.mock(SetupData.class);
        SystemSetupDataService service = new SystemSetupDataService(setupData);
        SystemSetupDataRequest request = new SystemSetupDataRequest();
        SystemSetupDataResponse response = service.executeRequest(request);
        Mockito.verify(setupData).setup();
    }
}
