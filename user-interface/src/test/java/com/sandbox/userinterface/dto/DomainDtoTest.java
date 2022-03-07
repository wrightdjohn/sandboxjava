package com.sandbox.userinterface.dto;

import com.sandbox.services.domain.Address;
import com.sandbox.services.domain.ContactPoint;
import com.sandbox.services.domain.Person;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DomainDtoTest {

    private AddressDto getAddrDto() {
        AddressDto dto = new AddressDto();
        dto.setAddressee("addressee");
        dto.setLine1("line1");
        dto.setLine2("line2");
        dto.setCity("city");
        dto.setState("state");
        dto.setPostalCode("postalCode");
        dto.setCountry("country");
        return dto;
    }

    private ContactPointDto getContactPointDto() {
        ContactPointDto dto = new ContactPointDto();
        dto.setUserName("userName");
        dto.setType("EMAIL");
        dto.setAuthenticated(true);
        return dto;
    }

    @Test
    public void whenSettingAddressFields_expectTheAnswers() {
        AddressDto dto = getAddrDto();
        assertThat(dto.getAddressee()).isEqualTo("addressee");
        assertThat(dto.getLine1()).isEqualTo("line1");
        assertThat(dto.getLine2()).isEqualTo("line2");
        assertThat(dto.getCity()).isEqualTo("city");
        assertThat(dto.getState()).isEqualTo("state");
        assertThat(dto.getPostalCode()).isEqualTo("postalCode");
        assertThat(dto.getCountry()).isEqualTo("country");

        Address addr = dto.toDomain();
        assertThat(addr.getAddressee()).isEqualTo("addressee");
        assertThat(addr.getLine1()).isEqualTo("line1");
        assertThat(addr.getLine2()).isEqualTo("line2");
        assertThat(addr.getCity()).isEqualTo("city");
        assertThat(addr.getState()).isEqualTo("state");
        assertThat(addr.getPostalCode()).isEqualTo("postalCode");
        assertThat(addr.getCountry()).isEqualTo("country");

        AddressDto dto2 = AddressDto.from(addr);
        assertThat(dto2.getAddressee()).isEqualTo("addressee");
        assertThat(dto2.getLine1()).isEqualTo("line1");
        assertThat(dto2.getLine2()).isEqualTo("line2");
        assertThat(dto2.getCity()).isEqualTo("city");
        assertThat(dto2.getState()).isEqualTo("state");
        assertThat(dto2.getPostalCode()).isEqualTo("postalCode");
        assertThat(dto2.getCountry()).isEqualTo("country");

    }

    @Test
    public void whenSettingContactPointFields_expectTheAnswers() {
        ContactPointDto dto = getContactPointDto();

        assertThat(dto.getUserName()).isEqualTo("userName");
        assertThat(dto.getType()).isEqualTo("EMAIL");
        assertThat(dto.isAuthenticated()).isTrue();

        ContactPoint cp = dto.toDomain();
        assertThat(cp.getUserName()).isEqualTo("userName");
        assertThat(cp.getType()).isEqualTo(ContactPoint.ContactPointType.EMAIL);
        assertThat(cp.isAuthenticated()).isTrue();

        ContactPointDto dto2 = ContactPointDto.from(cp);
        assertThat(dto2.getUserName()).isEqualTo("userName");
        assertThat(dto2.getType()).isEqualTo("EMAIL");
        assertThat(dto2.isAuthenticated()).isTrue();
    }


    @Test
    public void whenSettingPersonFields_expectTheAnswers() {
        PersonDto dto = new PersonDto();

        dto.setId("id");
        dto.setEmployeeId("employeeId");
        dto.setName("name");
        dto.setAddresses(Arrays.asList(getAddrDto()));
        dto.setContactPoints(Arrays.asList(getContactPointDto()));

        assertThat(dto.getId()).isEqualTo("id");
        assertThat(dto.getEmployeeId()).isEqualTo("employeeId");
        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getAddresses()).hasSize(1);
        assertThat(dto.getContactPoints()).hasSize(1);

        Person person = dto.toDomain();
        assertThat(person.getId().asText()).isEqualTo("id");
        assertThat(person.getEmployeeId()).isEqualTo("employeeId");
        assertThat(person.getName()).isEqualTo("name");
        assertThat(person.addresses().collect(Collectors.toList())).hasSize(1);
        assertThat(person.contactPoints().collect(Collectors.toList())).hasSize(1);

        Address addr = person.addresses().findFirst().get();
        assertThat(addr.getAddressee()).isEqualTo("addressee");
        assertThat(addr.getLine1()).isEqualTo("line1");
        assertThat(addr.getLine2()).isEqualTo("line2");
        assertThat(addr.getCity()).isEqualTo("city");
        assertThat(addr.getState()).isEqualTo("state");
        assertThat(addr.getPostalCode()).isEqualTo("postalCode");
        assertThat(addr.getCountry()).isEqualTo("country");

        ContactPoint cp = person.contactPoints().findFirst().get();
        assertThat(cp.getUserName()).isEqualTo("userName");
        assertThat(cp.getType()).isEqualTo(ContactPoint.ContactPointType.EMAIL);
        assertThat(cp.isAuthenticated()).isTrue();

        PersonDto dto2 = PersonDto.from(person);
        assertThat(dto2.getId()).isEqualTo("id");
        assertThat(dto2.getEmployeeId()).isEqualTo("employeeId");
        assertThat(dto2.getName()).isEqualTo("name");
        assertThat(dto2.getAddresses()).hasSize(1);
        assertThat(dto2.getContactPoints()).hasSize(1);

        AddressDto addrDto = dto2.getAddresses().get(0);
        assertThat(addrDto.getAddressee()).isEqualTo("addressee");
        assertThat(addrDto.getLine1()).isEqualTo("line1");
        assertThat(addrDto.getLine2()).isEqualTo("line2");
        assertThat(addrDto.getCity()).isEqualTo("city");
        assertThat(addrDto.getState()).isEqualTo("state");
        assertThat(addrDto.getPostalCode()).isEqualTo("postalCode");
        assertThat(addrDto.getCountry()).isEqualTo("country");

        ContactPointDto cpDto = dto2.getContactPoints().get(0);
        assertThat(cpDto.getUserName()).isEqualTo("userName");
        assertThat(cpDto.getType()).isEqualTo("EMAIL");
        assertThat(cpDto.isAuthenticated()).isTrue();

    }
}
