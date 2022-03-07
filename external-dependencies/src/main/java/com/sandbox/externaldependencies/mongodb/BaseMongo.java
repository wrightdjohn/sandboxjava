package com.sandbox.externaldependencies.mongodb;

import com.sandbox.services.core.BizId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BaseMongo {
    protected MongoClient client;
    protected String databaseName;
    protected String collectionName;

    protected BaseMongo(MongoClient client, String databaseName, String collectionName) {
        this.client = client;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public MongoCollection<Document> getCollection() {
        return client.getDatabase(databaseName).getCollection(collectionName);
    }

    protected ObjectId convertToMongoId(BizId id) {
        return new ObjectId(id.asText());
    }
}
