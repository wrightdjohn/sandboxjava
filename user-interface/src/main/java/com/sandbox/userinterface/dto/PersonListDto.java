package com.sandbox.userinterface.dto;

import com.sandbox.services.domain.Person;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonListDto {
    Collection<PersonDto> persons;

    public Collection<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(Collection<PersonDto> persons) {
        this.persons = persons;
    }

    public static PersonListDto from(Collection<Person> persons) {
        PersonListDto result = new PersonListDto();
        result.setPersons(persons.stream().map(p->PersonDto.from(p)).collect(Collectors.toList()));
        return result;
    }
}
