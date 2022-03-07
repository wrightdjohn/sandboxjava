package com.sandbox.services.implementations.personsave;

import com.sandbox.allsystemutils.exceptions.InvalidServiceRequest;
import com.sandbox.services.api.dao.Saver;
import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Address;
import com.sandbox.services.domain.ContactPoint;
import com.sandbox.services.domain.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.Mockito;

public class PersonSaveServiceTest {

    PersonSaveService service;
    Saver<Person> saver;

    @BeforeEach
    public void setup() {
        saver = Mockito.mock(Saver.class);
        service = new PersonSaveService(saver);
    }

    @Test
    public void whenValidRequest_expectCallToWithValidPerson() {
        Person person =
                Person.builder()
                        .name("name")
                        .employeeId("x999999")
                        .address(Address.builder().build())
                        .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE,"606-555-1212",true))
                        .build();

        Person respPerson = Person.builder().copyIn(person).id(new StringBizId("idStr")).build();

        PersonSaveRequest request = new PersonSaveRequest(person);
        Mockito.when(saver.save(anyList())).thenReturn(Arrays.asList(respPerson));
        PersonSaveResponse response = service.executeRequest(request);
        assertThat(response.getPersons()).hasSize(1);
        Person personOut = response.getPersons().get(0);
        assertThat(respPerson).isEqualTo(personOut);
    }

    @Test
    public void whenBadRequestNullPerson_expectException() {

        assertThatThrownBy(() -> {
            PersonSaveRequest request = new PersonSaveRequest((Person) null);
            service.executeRequest(request);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("No Person Object(s) passed to service");
    }

    @Test
    public void whenBadRequestNullPersonList_expectException() {

        assertThatThrownBy(() -> {
            PersonSaveRequest request = new PersonSaveRequest((List<Person>) null);
            service.executeRequest(request);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("No Person Object(s) passed to service");
    }

    @Test
    public void whenBadRequestEmptyPersonList_expectException() {

        assertThatThrownBy(() -> {
            PersonSaveRequest request = new PersonSaveRequest(new ArrayList<>());
            service.executeRequest(request);
        }).isInstanceOf(InvalidServiceRequest.class)
                .hasMessage("No Person Object(s) passed to service");
    }
}
