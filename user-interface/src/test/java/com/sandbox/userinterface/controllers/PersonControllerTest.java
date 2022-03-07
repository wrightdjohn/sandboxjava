package com.sandbox.userinterface.controllers;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.allsystemutils.json.JsonMapper;
import com.sandbox.services.api.Service;
import com.sandbox.services.domain.Address;
import com.sandbox.services.domain.ContactPoint;
import com.sandbox.services.domain.Person;
import com.sandbox.services.implementations.persondeletion.PersonDeletionRequest;
import com.sandbox.services.implementations.persondeletion.PersonDeletionResponse;
import com.sandbox.services.implementations.persondeletion.PersonDeletionService;
import com.sandbox.services.implementations.personsave.PersonSaveRequest;
import com.sandbox.services.implementations.personsave.PersonSaveResponse;
import com.sandbox.services.implementations.personsave.PersonSaveService;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalRequest;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalResponse;
import com.sandbox.services.implementations.personsretrieval.PersonsRetrievalService;
import com.sandbox.userinterface.dto.AddressDto;
import com.sandbox.userinterface.dto.ContactPointDto;
import com.sandbox.userinterface.dto.PersonDto;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import spark.Request;
import spark.Response;

public class PersonControllerTest {
    Request req;
    Response res;
    Service<PersonsRetrievalRequest, PersonsRetrievalResponse> personRetrievalService;
    Service<PersonDeletionRequest, PersonDeletionResponse> personDeletionService;
    Service<PersonSaveRequest, PersonSaveResponse> personSaveService;
    JsonMapper jsonMapper = new JsonMapper();
    PersonController personController;

    private String render(Object obj) {
        try {
            return jsonMapper.render(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to bad json");
        }
    }

    private ContactPointDto getContactPointDto() {
        ContactPointDto cpDto = new ContactPointDto();
        cpDto.setType("EMAIL");
        cpDto.setUserName("userName@company.org");
        return cpDto;
    }

    private AddressDto getAddressDto() {
        AddressDto addrDto = new AddressDto();
        addrDto.setAddressee("Mrs Janice Dawson");
        addrDto.setLine1("111 Fairlane Street");
        addrDto.setLine2("Apt 201");
        addrDto.setCity("Sunnydale");
        addrDto.setState("CA");
        addrDto.setPostalCode("61616");
        addrDto.setCountry("United States");
        return addrDto;
    }

    @BeforeEach
    public void setup() {
        req = Mockito.mock(Request.class);
        res = Mockito.mock(Response.class);
        personRetrievalService = Mockito.mock(PersonsRetrievalService.class);
        personDeletionService = Mockito.mock(PersonDeletionService.class);
        personSaveService = Mockito.mock(PersonSaveService.class);
        personController = new PersonController(personRetrievalService,  personDeletionService,  personSaveService, jsonMapper);
    }

    @Test
    public void whenDeleteRestCallIsMade_expectServiceRequestToBeCorrect() {
        Mockito.when(req.params(":id")).thenReturn("idStr");
        ArgumentCaptor<PersonDeletionRequest> argCaptor = ArgumentCaptor.forClass(PersonDeletionRequest.class);
        Mockito.when(personDeletionService.executeRequest(argCaptor.capture())).thenReturn(new PersonDeletionResponse());
        personController.deletePerson(req,res);
        PersonDeletionRequest serviceRequest = argCaptor.getValue();
        assertThat(serviceRequest.getId().asText()).isEqualTo("idStr");
    }

    @Test
    public void whenDeleteRestCallMadeWithoutId_expectException() {
        assertThatThrownBy(() -> {
            personController.deletePerson(req,res);
        }).isInstanceOf(InvalidServiceRequest.class)
          .hasMessage("Id is required if deleting a person");
    }

    @Test
    public void whenValidUpdatePersonRestCallIsMade_expectServiceRequestToBeCorrect() {
        PersonDto dto = new PersonDto() ;
        dto.setId("idStr");
        dto.setName("name");
        dto.setEmployeeId("employeeId");
        AddressDto addrDto = getAddressDto();
        ContactPointDto cpDto = getContactPointDto();
        cpDto.setAuthenticated(true);
        dto.setAddresses(Arrays.asList(addrDto));
        dto.setContactPoints(Arrays.asList(cpDto));
        String json = render(dto);

        Mockito.when(req.body()).thenReturn(json);
        ArgumentCaptor<PersonSaveRequest> argCaptor = ArgumentCaptor.forClass(PersonSaveRequest.class);
        Mockito.when(personSaveService.executeRequest(argCaptor.capture())).thenReturn(new PersonSaveResponse(Arrays.asList(dto.toDomain())));

        personController.updatePerson(req,res);
        PersonSaveRequest serviceRequest = argCaptor.getValue();

        assertThat(serviceRequest.getPersons()).hasSize(1);
        Person person = serviceRequest.getPersons().get(0);
        assertThat(person.getId().asText()).isEqualTo("idStr");
        assertThat(person.getName()).isEqualTo("name");
        assertThat(person.getEmployeeId()).isEqualTo("employeeId");
        assertThat(person.addresses().collect(Collectors.toList())).hasSize(1);
        Address address = person.addresses().findFirst().get();
        assertThat(address.getAddressee()).isEqualTo("Mrs Janice Dawson");
        assertThat(address.getLine1()).isEqualTo("111 Fairlane Street");
        assertThat(address.getLine2()).isEqualTo("Apt 201");
        assertThat(address.getCity()).isEqualTo("Sunnydale");
        assertThat(address.getState()).isEqualTo("CA");
        assertThat(address.getPostalCode()).isEqualTo("61616");
        assertThat(address.getCountry()).isEqualTo("United States");

        assertThat(person.contactPoints().collect(Collectors.toList())).hasSize(1);
        ContactPoint cp = person.contactPoints().findFirst().get();
        assertThat(cp.getType()).isEqualTo(ContactPoint.ContactPointType.EMAIL);
        assertThat(cp.getUserName()).isEqualTo("userName@company.org");
    }

    @Test
    public void whenUpdateRestCallMadeWithoutId_expectException() {
        PersonDto dto = new PersonDto() ;
        dto.setName("name");
        dto.setEmployeeId("employeeId");
        AddressDto addrDto = getAddressDto();
        ContactPointDto cpDto = getContactPointDto();
        cpDto.setAuthenticated(true);
        dto.setAddresses(Arrays.asList(addrDto));
        dto.setContactPoints(Arrays.asList(cpDto));
        String json = render(dto);

        Mockito.when(req.body()).thenReturn(json);

        assertThatThrownBy(() -> {
            personController.updatePerson(req,res);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("An update requires an existing id");
    }


    @Test
    public void whenValidInsertPersonRestCallIsMade_expectServiceRequestToBeCorrect() {
        PersonDto dto = new PersonDto() ;
        dto.setName("name");
        dto.setEmployeeId("employeeId");
        AddressDto addrDto = getAddressDto();
        ContactPointDto cpDto = getContactPointDto();
        cpDto.setAuthenticated(true);
        dto.setAddresses(Arrays.asList(addrDto));
        dto.setContactPoints(Arrays.asList(cpDto));
        String json = render(dto);

        Mockito.when(req.body()).thenReturn(json);
        ArgumentCaptor<PersonSaveRequest> argCaptor = ArgumentCaptor.forClass(PersonSaveRequest.class);
        Mockito.when(personSaveService.executeRequest(argCaptor.capture())).thenReturn(new PersonSaveResponse(Arrays.asList(dto.toDomain())));

        personController.insertPerson(req,res);
        PersonSaveRequest serviceRequest = argCaptor.getValue();

        assertThat(serviceRequest.getPersons()).hasSize(1);
        Person person = serviceRequest.getPersons().get(0);
        assertThat(person.getId()).isNull();
        assertThat(person.getName()).isEqualTo("name");
        assertThat(person.getEmployeeId()).isEqualTo("employeeId");
        assertThat(person.addresses().collect(Collectors.toList())).hasSize(1);
        Address address = person.addresses().findFirst().get();
        assertThat(address.getAddressee()).isEqualTo("Mrs Janice Dawson");
        assertThat(address.getLine1()).isEqualTo("111 Fairlane Street");
        assertThat(address.getLine2()).isEqualTo("Apt 201");
        assertThat(address.getCity()).isEqualTo("Sunnydale");
        assertThat(address.getState()).isEqualTo("CA");
        assertThat(address.getPostalCode()).isEqualTo("61616");
        assertThat(address.getCountry()).isEqualTo("United States");

        assertThat(person.contactPoints().collect(Collectors.toList())).hasSize(1);
        ContactPoint cp = person.contactPoints().findFirst().get();
        assertThat(cp.getType()).isEqualTo(ContactPoint.ContactPointType.EMAIL);
        assertThat(cp.getUserName()).isEqualTo("userName@company.org");
    }

    @Test
    public void whenInsertRestCallMadeWithId_expectException() {
        PersonDto dto = new PersonDto() ;
        dto.setId("idStr");
        dto.setName("name");
        dto.setEmployeeId("employeeId");
        AddressDto addrDto = getAddressDto();
        ContactPointDto cpDto = getContactPointDto();
        cpDto.setAuthenticated(true);
        dto.setAddresses(Arrays.asList(addrDto));
        dto.setContactPoints(Arrays.asList(cpDto));
        String json = render(dto);

        Mockito.when(req.body()).thenReturn(json);

        assertThatThrownBy(() -> {
            personController.insertPerson(req,res);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("Person has id and thus already exists so update rather than insert");
    }
}
