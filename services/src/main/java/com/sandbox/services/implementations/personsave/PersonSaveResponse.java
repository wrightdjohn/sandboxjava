package com.sandbox.services.implementations.personsave;

import com.sandbox.services.api.ServiceResponse;
import com.sandbox.services.domain.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonSaveResponse implements ServiceResponse {
    List<Person> persons;

    public PersonSaveResponse(Collection<Person> persons) {
        this.persons = new ArrayList<>(persons);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
