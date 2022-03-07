package com.sandbox.services.implementations.personsretrieval;

import com.sandbox.services.api.ServiceRequest;
import com.sandbox.services.api.dao.Criteria;
import com.sandbox.services.core.BizId;

public class PersonsRetrievalRequest implements ServiceRequest {
    private BizId personId;
    private Criteria criteria;

    public PersonsRetrievalRequest(BizId personId) {
        this.personId = personId;
    }

    public PersonsRetrievalRequest(Criteria criteria) {
        this.criteria = criteria;
    }

    public boolean isSinglePersonRequest() {
        return (personId != null);
    }

    public BizId getPersonId() {
        return personId;
    }

    public Criteria getCriteria() {
        return criteria;
    }
}
