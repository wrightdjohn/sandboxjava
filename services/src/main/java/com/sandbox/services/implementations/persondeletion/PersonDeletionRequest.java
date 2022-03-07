package com.sandbox.services.implementations.persondeletion;

import com.sandbox.services.api.ServiceRequest;
import com.sandbox.services.core.BizId;

public class PersonDeletionRequest implements ServiceRequest {
    private BizId id;

    public PersonDeletionRequest(BizId id) {
        this.id = id;
    }

    public BizId getId() {
        return id;
    }
}
