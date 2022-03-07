package com.sandbox.externaldependencies.mongodb.persondao;

import com.sandbox.externaldependencies.mongodb.Constants;
import com.sandbox.externaldependencies.mongodb.SetupDataMongo;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.domain.Person;
import com.sandbox.services.domain.StartupData;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SaverMongoTest {
    static String connectionString = "mongodb://localhost:27017/?directConnection=true&ssl=false";
    static MongoClient mongoClient = MongoClients.create(new ConnectionString(connectionString));
    static DocumentPersonMapper documentPersonMapper = new DocumentPersonMapper();
    static SetupDataMongo setupDataMongo;
    static Saver<Person> saver;
    static Finder<Person> finder;


    @BeforeAll
    public static void beforeAllSetup() {
        setupDataMongo = new SetupDataMongo(mongoClient, Constants.DATABASE,Constants.PEOPLE_COLLECTION,documentPersonMapper);
        saver = new SaverPersonMongo(mongoClient, Constants.DATABASE, Constants.PEOPLE_COLLECTION, documentPersonMapper);
        finder = new FinderPersonMongo(mongoClient, Constants.DATABASE, Constants.PEOPLE_COLLECTION, documentPersonMapper);
    }

    @BeforeEach
    public void setup() {
        setupDataMongo.setup();
    }

    @AfterAll
    public static void tearDown() {
        setupDataMongo.setup();
    }

    @Test
    public void whenSavingAnExistingPerson_expectChangesToStick() {
        Person person = Person.builder().copyIn(StartupData.jessicaWilde).employeeId("z232323").build();
        Person person2 = saver.save(person);
        assertThat(person2.getEmployeeId()).isEqualTo("z232323");
        Person newJessica = finder.findById(StartupData.jessicaWilde.getId()).get();
        assertThat(person2).isEqualTo(newJessica);
    }
}
