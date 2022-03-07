package com.sandbox.externaldependencies.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HealthCheckMongoTest {
    static String connectionString = "mongodb://localhost:27017/?directConnection=true&ssl=false";
    static MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
    static HealthCheckMongo healthCheckMongo;


    @BeforeAll
    public static void beforeAllSetup() {
        healthCheckMongo = new HealthCheckMongo(mongoClient,Constants.DATABASE,Constants.PEOPLE_COLLECTION);
    }

    @Test
    public void whenPersonIsDeleted_expectEmptyResponseWhenFinderSearchesForIt() {
        assertThat(healthCheckMongo.errorMessages()).isNullOrEmpty();
    }

}
