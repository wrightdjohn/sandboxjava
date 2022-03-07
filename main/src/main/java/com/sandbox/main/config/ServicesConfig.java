package com.sandbox.main.config;

import com.sandbox.main.dbconfig.DBConfig;
import com.sandbox.main.dbconfig.MongoDBConfig;
import com.sandbox.services.api.Service;
import com.sandbox.allsystemutils.exceptions.FatalSystemException;
import com.sandbox.services.implementations.persondeletion.PersonDeletionRequest;
import com.sandbox.services.implementations.persondeletion.PersonDeletionResponse;
import com.sandbox.services.implementations.persondeletion.PersonDeletionService;
import com.sandbox.services.implementations.personsave.PersonSaveRequest;
import com.sandbox.services.implementations.personsave.PersonSaveResponse;
import com.sandbox.services.implementations.personsave.PersonSaveService;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalRequest;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalResponse;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalService;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckRequest;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckResponse;
import com.sandbox.services.implementations.systemhealthcheck.SystemHealthCheckService;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataRequest;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataResponse;
import com.sandbox.services.implementations.systemsetupdata.SystemSetupDataService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServicesConfig {
    public static ServicesConfig instance = new ServicesConfig().setup();

    public DBConfig dao;
    public Service<PersonDeletionRequest, PersonDeletionResponse> personDeletionService;
    public Service<PersonsRetrievalRequest, PersonsRetrievalResponse> personRetrievalService;
    public Service<PersonSaveRequest, PersonSaveResponse> personSaveService;
    public Service<SystemHealthCheckRequest, SystemHealthCheckResponse> systemHealthCheckService;
    public Service<SystemSetupDataRequest, SystemSetupDataResponse> systemSetupDataService;

    public ServicesConfig setup() {
        loadProperties();
        personDeletionService = new PersonDeletionService(dao.getDeleterPerson());
        personRetrievalService = new PersonsRetrievalService(dao.getFinderPerson());
        personSaveService = new PersonSaveService(dao.getSaverPerson());
        systemHealthCheckService = new SystemHealthCheckService(dao.getHealthChecker());
        systemSetupDataService = new SystemSetupDataService(dao.getSetupData());
        return this;
    }

    private void loadProperties() {
        try(InputStream input = MongoDBConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new FatalSystemException("Missing application.properties file");
            }
            props.load(input);

            String databaseType = props.getProperty("database.type");
            if ("mongodb".equalsIgnoreCase(databaseType)) {
                dao = MongoDBConfig.instance;
            }
            else
            if ("mssql".equalsIgnoreCase(databaseType)) {
                dao = null; // for now
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
