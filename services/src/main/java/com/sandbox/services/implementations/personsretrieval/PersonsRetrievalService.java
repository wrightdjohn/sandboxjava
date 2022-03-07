package com.sandbox.services.implementations.personsretrieval;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.services.api.Service;
import com.sandbox.services.api.dao.Criteria;
import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.domain.Person;
import java.util.List;
import java.util.Optional;

public class PersonsRetrievalService implements Service<PersonsRetrievalRequest,PersonsRetrievalResponse> {

    private final Finder<Person> finder;

    public PersonsRetrievalService(Finder<Person> finder) {
        this.finder = finder;
    }

    @Override
    public PersonsRetrievalResponse executeRequest(PersonsRetrievalRequest request) {
        validate(request);
        if (request.isSinglePersonRequest()) {
            Optional<Person> optPerson = finder.findById(request.getPersonId());
            return new PersonsRetrievalResponse(optPerson.orElse(null));
        }
        else {
            Criteria criteria = request.getCriteria();
            List<Person> persons = finder.findBy(criteria);
            return new PersonsRetrievalResponse(persons);
        }
    }

    private void validate(PersonsRetrievalRequest request) {
        if (request.getPersonId() == null && request.getCriteria() == null) {
            throw new InvalidServiceRequest("Either criteria or a person id is required");
        }

        if (request.getPersonId() == null) {
            Criteria criteria = request.getCriteria();
            if (criteria.isEmpty()) {
                throw new InvalidServiceRequest("Either criteria or a person id is required");
            }
        }
    }
}
