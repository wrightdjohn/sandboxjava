package com.sandbox.externaldependencies.mongodb;

import com.sandbox.externaldependencies.mongodb.persondao.DocumentPersonMapper;
import com.sandbox.services.api.dao.SetupData;
import com.sandbox.services.domain.StartupData;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import java.util.stream.Collectors;
import org.bson.Document;

public class SetupDataMongo extends BaseMongo implements SetupData {
    DocumentPersonMapper documentMapper;

    public SetupDataMongo(MongoClient client, String databaseName, String collectionName, DocumentPersonMapper documentMapper) {
        super(client, databaseName, collectionName);
        this.documentMapper = documentMapper;
    }

    @Override
    public void setup() {
        MongoCollection<Document> collection = getCollection();
        collection.drop();

        collection.insertMany(StartupData.personList().stream().map(documentMapper::convert).collect(Collectors.toList()));

    }
}
