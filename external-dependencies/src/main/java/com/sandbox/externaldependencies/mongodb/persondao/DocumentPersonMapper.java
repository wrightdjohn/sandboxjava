package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Address;
import com.sandbox.services.domain.ContactPoint;
import com.sandbox.services.domain.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DocumentPersonMapper {


    public Person convert(Document document) {
        List<Document> addrDocs = (List<Document>) document.get("addresses");
        List<Document> cpDocs = (List<Document>) document.get("contactPoints");
        List<Address> addresses = addrDocs.stream().map(this::convertToAddress).collect(Collectors.toList());
        List<ContactPoint> contactPoints = cpDocs.stream().map(this::convertToContactPoint).collect(Collectors.toList());
        ObjectId oid = document.getObjectId("_id");
        String id = oid == null ? null : oid.toString();
        return Person.builder()
                .id(new StringBizId(id))
                .name(document.getString("name"))
                .employeeId(document.getString("employeeId"))
                .addresses(addresses)
                .contactPoints(contactPoints)
                .build();
    }

    private Address convertToAddress(Document document) {
        return
            Address.builder()
                    .addressee(document.getString("addressee"))
                    .line1(document.getString("line1"))
                    .line2(document.getString("line2"))
                    .city(document.getString("city"))
                    .state(document.getString("state"))
                    .postalCode(document.getString("postalCode"))
                    .country(document.getString("country"))
                    .build();
    }

    private ContactPoint convertToContactPoint(Document document) {
        return new ContactPoint(
                ContactPoint.ContactPointType.valueOf(document.getString("type")),
                document.getString("userName"),
                document.getBoolean("authenticated"));
    }

    public Document convert(Person person) {
        Document doc = new Document();
        if (person.getId() != null) {
            put(doc, "_id", new ObjectId(person.getId().asText()));
        }
        put(doc, "name", person.getName());
        put(doc, "employeeId", person.getEmployeeId());
        processAddresses(doc, person);
        processContactPoints(doc, person);
        return doc;
    }

    private void processAddresses(Document doc, Person person) {
        List<Document> addressDocs = new ArrayList<>();
        person.addresses().forEach(address->{
            Document docAddr = new Document();
            put(docAddr,"addressee",address.getAddressee());
            put(docAddr,"line1",address.getLine1());
            put(docAddr,"line2",address.getLine2());
            put(docAddr, "city", address.getCity());
            put(docAddr, "state", address.getState());
            put(docAddr, "postalCode", address.getPostalCode());
            put(docAddr, "country", address.getCountry());
            addressDocs.add(docAddr);
        });
        put(doc, "addresses", addressDocs);
    }

    private void processContactPoints(Document doc, Person person) {
        List<Document> contactPoints = new ArrayList<>();
        person.contactPoints().forEach(cp->{
            Document docCp = new Document();
            put(docCp, "type", cp.getType().name());
            put(docCp, "userName", cp.getUserName());
            put(docCp, "authenticated", cp.isAuthenticated());
            contactPoints.add(docCp);
        });
        put(doc, "contactPoints", contactPoints);
    }

    private void put(Document doc, String key, String value) {
        if (value == null) return;
        doc.put(key,value);
    }

    private void put(Document doc, String key, Boolean value) {
        if (value == null) return;
        doc.put(key,value);
    }

    private void put(Document doc, String key, ObjectId value) {
        if (value == null) return;
        doc.put(key,value);
    }

    private void put(Document doc, String key, List<Document> values) {
        if (values == null) return;
        doc.put(key,values);
    }

}
