package com.sandbox.services.implementations.personsave;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.services.api.ServiceRequest;
import com.sandbox.services.domain.Person;
import java.util.Arrays;
import java.util.List;

public class PersonSaveRequest implements ServiceRequest {
    List<Person> persons;

    public PersonSaveRequest(List<Person> persons) {
        if (persons == null || persons.isEmpty()) {
            throw new InvalidServiceRequest("No Person Object(s) passed to service");
        }
        this.persons = persons;
    }

    public PersonSaveRequest(Person person) {
        if (person == null) {
            throw new InvalidServiceRequest("No Person Object(s) passed to service");
        }
        this.persons = Arrays.asList(person);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
