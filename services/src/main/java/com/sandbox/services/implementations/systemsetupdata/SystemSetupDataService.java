package com.sandbox.services.implementations.systemsetupdata;

import com.sandbox.services.api.Service;
import com.sandbox.services.api.dao.SetupData;

public class SystemSetupDataService implements Service<SystemSetupDataRequest,SystemSetupDataResponse> {
    SetupData setupData;

    public SystemSetupDataService(SetupData setupData) {
        this.setupData = setupData;
    }

    @Override
    public SystemSetupDataResponse executeRequest(SystemSetupDataRequest request) {
        setupData.setup();

        return new SystemSetupDataResponse();
    }
}
