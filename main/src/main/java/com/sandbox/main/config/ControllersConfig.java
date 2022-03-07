package com.sandbox.main.config;

import com.sandbox.allsystemutils.json.JsonMapper;
import com.sandbox.userinterface.controllers.PersonController;
import com.sandbox.userinterface.controllers.SystemController;

public class ControllersConfig {
    public static ControllersConfig instance = new ControllersConfig().setup();

    public ServicesConfig services = ServicesConfig.instance;

    public JsonMapper jsonMapper = new JsonMapper();
    public SystemController systemController;
    public PersonController personController;

    public ControllersConfig setup() {

        systemController = new SystemController(services.systemHealthCheckService,services.systemSetupDataService);
        personController = new PersonController(services.personRetrievalService,services.personDeletionService,services.personSaveService,jsonMapper);

        return this;
    }
}
