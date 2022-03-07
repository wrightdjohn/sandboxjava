package com.sandbox.main.dbconfig;

import com.sandbox.externaldependencies.mongodb.HealthCheckMongo;
import com.sandbox.externaldependencies.mongodb.SetupDataMongo;
import com.sandbox.externaldependencies.mongodb.persondao.DeleterPersonMongo;
import com.sandbox.externaldependencies.mongodb.persondao.DocumentPersonMapper;
import com.sandbox.externaldependencies.mongodb.persondao.FinderPersonMongo;
import com.sandbox.externaldependencies.mongodb.persondao.SaverPersonMongo;
import com.sandbox.services.api.dao.Deleter;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.api.general.HealthChecker;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.api.dao.SetupData;
import com.sandbox.services.domain.Person;
import com.sandbox.allsystemutils.exceptions.FatalSystemException;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBConfig implements DBConfig {
    public static DBConfig instance = new MongoDBConfig().setup();
    private String connectionString;
    private String databaseName;
    private String peopleCollectionName;

    private MongoClient mongoClient;
    private DocumentPersonMapper documentPersonMapper;

    public Finder<Person> finderPersonMongo;
    public Saver<Person> saverPersonMongo;
    public Deleter deleterPersonMongo;
    public HealthChecker healthChecker;
    public SetupData setupData;

    public Finder<Person> getFinderPerson() {
        return finderPersonMongo;
    }

    public Saver<Person> getSaverPerson() {
        return saverPersonMongo;
    }

    public Deleter getDeleterPerson() {
        return deleterPersonMongo;
    }

    public HealthChecker getHealthChecker() {
        return healthChecker;
    }

    public SetupData getSetupData() {
        return setupData;
    }

    private MongoDBConfig() {

    }

    public MongoDBConfig setup() {
        loadProperties();

        mongoClient = MongoClients.create(new ConnectionString(connectionString));
        documentPersonMapper = new DocumentPersonMapper();

        finderPersonMongo = new FinderPersonMongo(mongoClient, databaseName, peopleCollectionName, documentPersonMapper);
        saverPersonMongo = new SaverPersonMongo(mongoClient, databaseName, peopleCollectionName, documentPersonMapper);
        deleterPersonMongo = new DeleterPersonMongo(mongoClient,databaseName, peopleCollectionName);
        healthChecker = new HealthCheckMongo(mongoClient,databaseName, peopleCollectionName);
        setupData = new SetupDataMongo(mongoClient,databaseName, peopleCollectionName,documentPersonMapper);

        return this;
    }

    private void loadProperties() {
        try(InputStream input = MongoDBConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new FatalSystemException("Missing application.properties file");
            }
            props.load(input);

            this.connectionString = props.getProperty("mongodb.connection");
            this.databaseName = props.getProperty("mongodb.databaseName");
            this.peopleCollectionName = props.getProperty("mongodb.collectionName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
