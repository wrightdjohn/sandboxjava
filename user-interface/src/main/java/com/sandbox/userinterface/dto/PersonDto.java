package com.sandbox.userinterface.dto;

import com.sandbox.services.core.StringBizId;
import com.sandbox.services.domain.Person;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDto {
    private String id;
    private String name;
    private String employeeId;
    private List<ContactPointDto> contactPoints;
    private List<AddressDto> addresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<ContactPointDto> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(List<ContactPointDto> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public static PersonDto from(Person person) {
        PersonDto result = new PersonDto();
        result.id = person.getId() == null ? null : person.getId().asText();
        result.name = person.getName();
        result.employeeId = person.getEmployeeId();
        result.contactPoints = person.contactPoints().map(ContactPointDto::from).collect(Collectors.toList());
        result.addresses = person.addresses().map(AddressDto::from).collect(Collectors.toList());
        return result;
    }

    public Person toDomain() {
        return
            Person.builder()
                .id(this.id == null ? null : new StringBizId(this.id))
                .name(this.name)
                .employeeId(this.employeeId)
                .contactPoints(this.contactPoints.stream().map(ContactPointDto::toDomain).collect(Collectors.toList()))
                .addresses(this.addresses.stream().map(AddressDto::toDomain).collect(Collectors.toList()))
                .build();
    }
}
