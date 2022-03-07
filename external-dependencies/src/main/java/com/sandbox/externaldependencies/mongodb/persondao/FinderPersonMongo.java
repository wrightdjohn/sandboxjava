package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.externaldependencies.mongodb.BaseMongo;
import com.sandbox.services.api.dao.Criteria;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.core.BizId;
import com.sandbox.services.core.CriteriaElement;
import com.sandbox.services.domain.Person;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalCriteria;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;

public class FinderPersonMongo extends BaseMongo implements Finder<Person> {
    DocumentPersonMapper documentMapper;

    public FinderPersonMongo(MongoClient client, String databaseName, String collectionName, DocumentPersonMapper documentMapper) {
        super(client, databaseName, collectionName);
        this.documentMapper = documentMapper;
    }

    @Override
    public Optional<Person> findById(BizId id) {
        MongoCollection<Document> collection = getCollection();
        Document query = new Document();
        query.put("_id",convertToMongoId(id));
        Document doc = collection.find(query).first();
        if (doc == null) {
            return Optional.empty();
        }
        return Optional.of(documentMapper.convert(doc));
    }

    @Override
    public List<Person> findBy(Criteria criteria) {
        PersonsRetrievalCriteria prc = (PersonsRetrievalCriteria) criteria;
        List<Bson> queryElements = new ArrayList<>();
        for (CriteriaElement element:prc.getCriteriaElements()) {
            processElement(queryElements, element);
        }
        Bson andFilter = Filters.and(queryElements.toArray(new Bson[0]));
        FindIterable<Document> documents = getCollection().find(andFilter);
        List<Person> result = new ArrayList<>();
        for (Document doc:documents) {
            result.add(documentMapper.convert(doc));
        }
        return result;
    }

    private void processElement(List<Bson> queryElements, CriteriaElement ce) {
        if (ce == null) return;
        if (ce.getOperation() == null) return;

        switch (ce.getOperation()) {
            case EQUAL_TO -> queryElements.add(Filters.eq(ce.getName(), ce.getValue()));
            case CONTAINS -> queryElements.add(Filters.regex(ce.getName(), Pattern.compile(".*" + ce.getValue() + ".*", Pattern.CASE_INSENSITIVE)));
            case STARTS_WITH -> queryElements.add(Filters.regex(ce.getName(), Pattern.compile("^" + ce.getValue() + ".*", Pattern.CASE_INSENSITIVE)));
            case ENDS_WITH -> queryElements.add(Filters.regex(ce.getName(), Pattern.compile(".*" + ce.getValue() + "$", Pattern.CASE_INSENSITIVE)));
            case INLIST -> queryElements.add(Filters.in(ce.getName(), ce.getValues()));
            case LESS_THAN -> queryElements.add(Filters.lt(ce.getName(), ce.getValue()));
            case LESS_THAN_OR_EQUAL -> queryElements.add(Filters.lte(ce.getName(), ce.getValue()));
            case GREATER_THAN -> queryElements.add(Filters.gt(ce.getName(), ce.getValue()));
            case GREATER_THAN_OR_EQUAL -> queryElements.add(Filters.gte(ce.getName(), ce.getValue()));
        }
    }
}
