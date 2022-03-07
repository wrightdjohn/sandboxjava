package com.sandbox.externaldependencies.mongodb;

import com.sandbox.services.api.general.HealthChecker;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class HealthCheckMongo extends BaseMongo implements HealthChecker {
    public HealthCheckMongo(MongoClient client, String databaseName, String collectionName) {
        super(client, databaseName, collectionName);
    }

    public boolean canConnect() {
        MongoDatabase database = client.getDatabase("__demo");
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            return true;
        }
        catch (MongoException me) {
            return false;
        }
    }

    @Override
    public String errorMessages() {
        if (canConnect()) return null;
        return "Unable to connect to Mongo DB";
    }
}
