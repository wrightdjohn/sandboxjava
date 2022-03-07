package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.externaldependencies.mongodb.BaseMongo;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.domain.Person;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bson.Document;

public class SaverPersonMongo extends BaseMongo implements Saver<Person> {
    private DocumentPersonMapper documentMapper;

    public SaverPersonMongo(MongoClient client, String databaseName, String collectionName, DocumentPersonMapper documentMapper) {
        super(client, databaseName, collectionName);
        this.documentMapper = documentMapper;
    }

    @Override
    public Person save(Person person) {
       MongoCollection<Document> collection = getCollection();
       Document personDoc = documentMapper.convert(person);
       if (person.getId() == null) {
           collection.insertOne(personDoc);
       }
       else {
           Document updateDoc = new Document();
           updateDoc.put("$set",personDoc);
           collection.updateOne(Filters.eq("_id",convertToMongoId(person.getId())),updateDoc);
       }
       return documentMapper.convert(personDoc);
    }

    @Override
    public Collection<Person> save(Collection<Person> persons) {
       return persons.stream().map(this::save).collect(Collectors.toList());
    }
}
