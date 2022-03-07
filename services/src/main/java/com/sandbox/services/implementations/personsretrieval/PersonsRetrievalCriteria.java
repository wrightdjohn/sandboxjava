package com.sandbox.services.implementations.personsretrieval;

import com.sandbox.services.api.dao.Criteria;
import com.sandbox.services.core.CriteriaElement;
import java.util.ArrayList;
import java.util.List;

public class PersonsRetrievalCriteria implements Criteria {
    private List<CriteriaElement> elements = new ArrayList<>();

    public List<CriteriaElement> getCriteriaElements() {
        return elements;
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    private boolean notEmpty(String s) {
        if (s == null) return false;
        if (s.trim().length() == 0) return false;
        return true;
    }

    public PersonsRetrievalCriteria nameIsEqualTo(String nameValue) {
        if (notEmpty(nameValue)) {
            elements.add(CriteriaElement.equalTo("name", nameValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria nameStartsWith(String nameValue) {
        if (notEmpty(nameValue)) {
            elements.add(CriteriaElement.startsWith("name", nameValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria nameEndsWith(String nameValue) {
        if (notEmpty(nameValue)) {
            elements.add(CriteriaElement.endsWith("name", nameValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria nameContains(String nameValue) {
        if (notEmpty(nameValue)) {
            elements.add(CriteriaElement.contains("name", nameValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria employeeIdIsEqualTo(String employeeIdValue) {
        if (notEmpty(employeeIdValue)) {
            elements.add(CriteriaElement.equalTo("employeeId", employeeIdValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria hasAddressWithCity(String cityValue) {
        if (notEmpty(cityValue)) {
            elements.add(CriteriaElement.equalTo("addresses.city", cityValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria hasAddressWithState(String stateValue) {
        if (notEmpty(stateValue)) {
            elements.add(CriteriaElement.equalTo("addresses.state", stateValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria hasAddressWithPostalCode(String postalCodeValue) {
        if (notEmpty(postalCodeValue)) {
            elements.add(CriteriaElement.equalTo("addresses.postalCode", postalCodeValue));
        }
        return this;
    }

    public PersonsRetrievalCriteria hasAddressWithCountry(String countryValue) {
        if (notEmpty(countryValue)) {
            elements.add(CriteriaElement.equalTo("addresses.country", countryValue));
        }
        return this;
    }

}
