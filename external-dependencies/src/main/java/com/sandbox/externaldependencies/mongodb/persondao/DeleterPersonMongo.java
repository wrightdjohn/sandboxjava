package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.externaldependencies.mongodb.BaseMongo;
import com.sandbox.services.api.dao.Deleter;
import com.sandbox.services.core.BizId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;

public class DeleterPersonMongo extends BaseMongo implements Deleter {

    public DeleterPersonMongo(MongoClient client, String databaseName, String collectionName) {
        super(client, databaseName, collectionName);
    }

    @Override
    public void delete(BizId id) {
        getCollection().deleteOne(Filters.eq("_id",convertToMongoId(id)));
    }
}
