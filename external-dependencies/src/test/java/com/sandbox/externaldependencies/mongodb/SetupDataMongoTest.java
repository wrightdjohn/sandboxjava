package com.sandbox.externaldependencies.mongodb;

import com.sandbox.externaldependencies.mongodb.persondao.DocumentPersonMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetupDataMongoTest {

    String connectionString = "mongodb://localhost:27017/?directConnection=true&ssl=false";
    MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
    DocumentPersonMapper documentPersonMapper = new DocumentPersonMapper();
    SetupDataMongo setupDataMongo;

    @BeforeEach
    public void setup() {
        setupDataMongo = new SetupDataMongo(mongoClient,Constants.DATABASE,Constants.PEOPLE_COLLECTION,documentPersonMapper);
    }

    @Test
    public void whenSetupIsRan_expectStarterData() {
       setupDataMongo.setup();

    }
}
