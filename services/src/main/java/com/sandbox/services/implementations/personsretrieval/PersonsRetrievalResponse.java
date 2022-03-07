package com.sandbox.services.implementations.personsretrieval;

import com.sandbox.services.api.ServiceResponse;
import com.sandbox.services.domain.Person;
import java.util.Arrays;
import java.util.List;

public class PersonsRetrievalResponse implements ServiceResponse {
    private List<Person> persons;

    public PersonsRetrievalResponse(List<Person> persons) {
        this.persons = persons;
    }

    public PersonsRetrievalResponse(Person person) {
        this.persons = Arrays.asList(person);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
