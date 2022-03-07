package com.sandbox.services.implementations.personsave;

import com.sandbox.services.api.Service;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.domain.Person;
import java.util.Collection;

public class PersonSaveService implements Service<PersonSaveRequest,PersonSaveResponse> {
    Saver<Person> saver;

    public PersonSaveService(Saver<Person> saver) {
        this.saver = saver;
    }

    @Override
    public PersonSaveResponse executeRequest(PersonSaveRequest request) {
        Collection<Person> persons = saver.save(request.getPersons());
        return new PersonSaveResponse(persons);
    }
}
