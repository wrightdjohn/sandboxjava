package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.externaldependencies.mongodb.Constants;
import com.sandbox.externaldependencies.mongodb.SetupDataMongo;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Address;
import com.sandbox.services.domain.ContactPoint;
import com.sandbox.services.domain.Person;
import com.sandbox.services.domain.StartupData;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalCriteria;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinderMongoTest {
    static String connectionString = "mongodb://localhost:27017/?directConnection=true&ssl=false";
    static MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
    static DocumentPersonMapper documentPersonMapper = new DocumentPersonMapper();
    static SetupDataMongo setupDataMongo;
    static Finder<Person> finder;

    @BeforeAll
    public static void beforeAllSetup() {
        setupDataMongo = new SetupDataMongo(mongoClient,Constants.DATABASE,Constants.PEOPLE_COLLECTION,documentPersonMapper);
        finder = new FinderPersonMongo(mongoClient, Constants.DATABASE, Constants.PEOPLE_COLLECTION, documentPersonMapper);
    }

    @BeforeEach
    public void setup() {
        setupDataMongo.setup();
    }

    @AfterAll
    public static void tearDownAfterAll() {
        setupDataMongo.setup();
    }

    @Test
    public void afterStartUpDataIsInserted_expectFinderByIdToReturnValidPerson() {
       Optional<Person> optPer = finder.findById(StartupData.joeBlack.getId());
       assertThat(optPer).isNotEmpty();
       Person person = optPer.get();
       assertThat(person).isEqualTo(StartupData.joeBlack);
    }

    @Test
    public void whenPersonHasTwoAddresses_expectMatchRegardlessOfOrder() {
        Person preetaSmithBackwards =
                Person.builder()
                        .id(new StringBizId("6220fc388c70850875b5af0f"))
                        .name("Preeta Smith")
                        .employeeId("y323554")
                        .address(Address.builder().copyIn(StartupData.nycOffice).addressee("Preeta Smith").build())
                        .address(Address.builder().copyIn(StartupData.mainOffice).addressee("Preeta Smith").build())
                        .contactPoint(new ContactPoint(ContactPoint.ContactPointType.EMAIL, "preeta.smith@bigbiz.com", true))
                        .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "716-555-5423", true))
                        .build();

        Optional<Person> optPer = finder.findById(StartupData.preetaSmith.getId());
        assertThat(optPer).isNotEmpty();
        Person person = optPer.get();
        assertThat(person).isEqualTo(StartupData.preetaSmith);
        assertThat(person).isEqualTo(preetaSmithBackwards);

    }

    @Test
    public void whenPersonIsSearchedByCityCriteria_expectMatches() {
        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.hasAddressWithCity(StartupData.mainOffice.getCity());
        List<Person> persons = finder.findBy(criteria);
        assertThat(persons).hasSize(4);
        assertThat(persons).containsExactlyInAnyOrder(StartupData.joeBlack,StartupData.jimBorman, StartupData.jessicaWilde, StartupData.preetaSmith);
    }

    @Test
    public void whenPersonIsSearchedByNameEqualsWithCriteria_expectMatches() {
        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.nameIsEqualTo("James L. Borman");
        List<Person> persons = finder.findBy(criteria);
        assertThat(persons).hasSize(1);
        assertThat(persons).containsExactlyInAnyOrder(StartupData.jimBorman);
    }

    @Test
    public void whenPersonIsSearchedByNameStartsWithCriteria_expectMatches() {
        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.nameStartsWith("J");
        List<Person> persons = finder.findBy(criteria);
        assertThat(persons).hasSize(3);
        assertThat(persons).containsExactlyInAnyOrder(StartupData.joeBlack,StartupData.jimBorman, StartupData.jessicaWilde);
    }

    @Test
    public void whenPersonIsSearchedByNameEndsWithCriteria_expectMatches() {
        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.nameEndsWith("e");
        List<Person> persons = finder.findBy(criteria);
        assertThat(persons).hasSize(3);
        assertThat(persons).containsExactlyInAnyOrder(StartupData.jessicaWilde,StartupData.martinLake,StartupData.virginiaOToole);
    }

    @Test
    public void whenPersonIsSearchedByNameContainsCriteria_expectMatches() {
        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.nameContains("la");
        List<Person> persons = finder.findBy(criteria);
        assertThat(persons).hasSize(3);
        assertThat(persons).containsExactlyInAnyOrder(StartupData.joeBlack,StartupData.jessicaWilde, StartupData.martinLake);
    }
}
