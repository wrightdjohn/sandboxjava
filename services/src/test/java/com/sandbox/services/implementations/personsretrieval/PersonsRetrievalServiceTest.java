package com.sandbox.services.implementations.personsretrieval;

import com.sandbox.services.api.dao.Finder;
import com.sandbox.services.core.BizId;
import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Person;
import java.util.Arrays;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PersonsRetrievalServiceTest {
    PersonsRetrievalService service;
    Finder<Person> finder;

    @BeforeEach
    public void setup() {
        finder = Mockito.mock(Finder.class);
        service = new PersonsRetrievalService(finder);
    }

    @Test
    public void whenFindingById_expectGoodResult() {
        BizId id = new StringBizId("idStr");
        Person person =
           Person.builder()
                       .id(id)
                       .name("name")
                       .employeeId("employeeId")
                       .build();

        Mockito.when(finder.findById(id)).thenReturn(Optional.of(person));
        PersonsRetrievalResponse response = service.executeRequest(new PersonsRetrievalRequest(id));
        assertThat(response.getPersons()).hasSize(1);
        Person personOut = response.getPersons().get(0);
        assertThat(personOut).isEqualTo(person);
    }

    @Test
    public void whenFindingByCriteria_expectGoodResult() {
        BizId id = new StringBizId("idStr");
        Person person =
                Person.builder()
                        .id(id)
                        .name("name")
                        .employeeId("employeeId")
                        .build();

        BizId id2 = new StringBizId("idStr2");
        Person person2 =
                Person.builder()
                        .id(id2)
                        .name("name2")
                        .employeeId("employeeId2")
                        .build();

        PersonsRetrievalCriteria criteria = new PersonsRetrievalCriteria();
        criteria.nameStartsWith("name");
        Mockito.when(finder.findBy(criteria)).thenReturn(Arrays.asList(person,person2));
        PersonsRetrievalResponse response = service.executeRequest(new PersonsRetrievalRequest(criteria));
        assertThat(response.getPersons()).hasSize(2);
        Person personOut = response.getPersons().get(0);
        assertThat(personOut).isEqualTo(person);
        Person personOut2 = response.getPersons().get(1);
        assertThat(personOut2).isEqualTo(person2);
    }
}
