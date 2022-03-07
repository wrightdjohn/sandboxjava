package com.sandbox.userinterface.controllers;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.allsystemutils.json.JsonMapper;
import com.sandbox.services.api.Service;
import com.sandbox.services.core.BizId;
import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Person;
import com.sandbox.services.implementations.persondeletion.PersonDeletionRequest;
import com.sandbox.services.implementations.persondeletion.PersonDeletionResponse;
import com.sandbox.services.implementations.personsave.PersonSaveRequest;
import com.sandbox.services.implementations.personsave.PersonSaveResponse;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalCriteria;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalRequest;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalResponse;
import com.sandbox.userinterface.dto.PersonDto;
import com.sandbox.userinterface.dto.PersonListDto;
import org.eclipse.jetty.http.HttpStatus;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

public class PersonController {
    private Service<PersonsRetrievalRequest, PersonsRetrievalResponse> personRetrievalService;
    private Service<PersonDeletionRequest, PersonDeletionResponse> personDeletionService;
    private Service<PersonSaveRequest, PersonSaveResponse> personSaveService;
    private JsonMapper jsonMapper;

    public PersonController(Service<PersonsRetrievalRequest, PersonsRetrievalResponse> personRetrievalService, Service<PersonDeletionRequest, PersonDeletionResponse> personDeletionService, Service<PersonSaveRequest, PersonSaveResponse> personSaveService, JsonMapper jsonMapper) {
        this.personRetrievalService = personRetrievalService;
        this.personDeletionService = personDeletionService;
        this.personSaveService = personSaveService;
        this.jsonMapper = jsonMapper;
    }

    public Object getPersonById(Request req, Response res) {
        BizId id = new StringBizId(req.params(":id"));
        PersonsRetrievalRequest request = new PersonsRetrievalRequest(id);

        PersonsRetrievalResponse response = personRetrievalService.executeRequest(request);
        if (response.getPersons().isEmpty()) {
            res.status(HttpStatus.NO_CONTENT_204);
            return null;
        }
        else {
            res.status(HttpStatus.OK_200);
            return response.getPersons().get(0);
        }
    }

    public Object getPersonByCriteria(Request req, Response res) {
        QueryParamsMap qpm = req.queryMap();
        PersonsRetrievalCriteria crit = new PersonsRetrievalCriteria();
        crit.employeeIdIsEqualTo(qpm.value("employeeIdIsEqualTo"));
        crit.nameIsEqualTo(qpm.value("nameIsEqualTo"));
        crit.nameContains(qpm.value("nameContains"));
        crit.nameStartsWith(qpm.value("nameStartsWith"));
        crit.nameEndsWith(qpm.value("nameEndsWith"));
        crit.hasAddressWithCity(qpm.value("hasAddressWithCity"));
        crit.hasAddressWithState(qpm.value("hasAddressWithState"));
        crit.hasAddressWithCountry(qpm.value("hasAddressWithCountry"));
        crit.hasAddressWithPostalCode(qpm.value("hasAddressWithPostalCode"));
        crit.employeeIdIsEqualTo(qpm.value("employeeIdIsEqualTo"));
        PersonsRetrievalRequest request = new PersonsRetrievalRequest(crit);
        PersonsRetrievalResponse response = personRetrievalService.executeRequest(request);
        return PersonListDto.from(response.getPersons());
    }

    public Object insertPerson(Request req, Response res) {
        PersonDto dto = (PersonDto) jsonMapper.parse(req.body(), PersonDto.class);
        if (dto == null) {
            throw new InvalidServiceRequest("No Person sent in body");
        }
        Person person = dto.toDomain();
        if (person.getId() != null) {
            throw new InvalidServiceRequest("Person has id and thus already exists so update rather than insert");
        }
        PersonSaveRequest request = new PersonSaveRequest(person);
        PersonSaveResponse response = personSaveService.executeRequest(request);
        return PersonDto.from(response.getPersons().get(0));
    }

    public Object updatePerson(Request req, Response res) {
        PersonDto dto = (PersonDto) jsonMapper.parse(req.body(), PersonDto.class);
        if (dto == null || dto.getId() == null) {
            throw new InvalidServiceRequest("An update requires an existing id");
        }
        Person person = dto.toDomain();
        PersonSaveRequest request = new PersonSaveRequest(person);
        PersonSaveResponse response = personSaveService.executeRequest(request);
        return PersonDto.from(response.getPersons().get(0));
    }

    public Object deletePerson(Request req, Response res) {
        String idStr = req.params(":id");
        if (idStr == null) {
            throw new InvalidServiceRequest("Id is required if deleting a person");
        }
        BizId id = new StringBizId(idStr);
        PersonDeletionRequest request = new PersonDeletionRequest(id);
        personDeletionService.executeRequest(request);
        res.status(HttpStatus.OK_200);
        return "success";
    }
}
