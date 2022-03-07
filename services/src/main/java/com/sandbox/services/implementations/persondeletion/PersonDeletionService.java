package com.sandbox.services.implementations.persondeletion;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.services.api.Service;
import com.sandbox.services.api.dao.Deleter;

public class PersonDeletionService implements Service<PersonDeletionRequest,PersonDeletionResponse> {
    private Deleter deleter;

    public PersonDeletionService(Deleter deleter) {
        this.deleter = deleter;
    }

    @Override
    public PersonDeletionResponse executeRequest(PersonDeletionRequest request) {
        if (request.getId() == null) {
            throw new InvalidServiceRequest("Id passed for deletion was null");
        }
        deleter.delete(request.getId());
        return new PersonDeletionResponse();
    }
}
