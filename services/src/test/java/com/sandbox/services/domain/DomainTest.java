package com.sandbox.services.domain;


import com.sandbox.services.core.StringBizId;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DomainTest {
    private Address getAddress() {
        Address address =
                Address.builder()
                        .addressee("Joe Blaine")
                        .line1("1706 Edgewood Drive")
                        .line2("Apt 532")
                        .city("Munsford")
                        .state("RI")
                        .postalCode("12345")
                        .country("United States")
                        .build();
        return address;
    }

    @Test
    public void whenAddressIsPopulated_expectBackSameData() {
        Address address = getAddress();

        assertThat(address.getAddressee()).isEqualTo("Joe Blaine");
        assertThat(address.getLine1()).isEqualTo("1706 Edgewood Drive");
        assertThat(address.getLine2()).isEqualTo("Apt 532");
        assertThat(address.getCity()).isEqualTo("Munsford");
        assertThat(address.getState()).isEqualTo("RI");
        assertThat(address.getPostalCode()).isEqualTo("12345");
        assertThat(address.getCountry()).isEqualTo("United States");

    }

    @Test
    public void whenContactPointIsPopulated_expectBackSameData() {
        ContactPoint cp = new ContactPoint(ContactPoint.ContactPointType.PHONE,"606-555-1212", true);
        assertThat(cp.getType()).isEqualTo(ContactPoint.ContactPointType.PHONE);
        assertThat(cp.getUserName()).isEqualTo("606-555-1212");
        assertThat(cp.isAuthenticated()).isTrue();
    }

    @Test
    public void whenPersonIsPopulated_expectBackSameData() {
        Person one =
            Person.builder()
                .id(new StringBizId("idStr"))
                .name("Jessica Blaine")
                .employeeId("x999999")
                .addresses(Arrays.asList(getAddress()))
                .contactPoints(Arrays.asList(new ContactPoint(ContactPoint.ContactPointType.PHONE,"606-555-1212", true)))
                .build();

        Person two =
                Person.builder()
                        .id(new StringBizId("idStr"))
                        .name("Jessica Blaine")
                        .employeeId("x999999")
                        .address(getAddress())
                        .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE,"606-555-1212", true))
                        .build();

        assertThat(one).isEqualTo(two);
        assertThat(one.getId().asText()).isEqualTo("idStr");
        assertThat(one.getName()).isEqualTo("Jessica Blaine");
        assertThat(one.getEmployeeId()).isEqualTo("x999999");
        assertThat(two.getId().asText()).isEqualTo("idStr");
        assertThat(two.getName()).isEqualTo("Jessica Blaine");
        assertThat(two.getEmployeeId()).isEqualTo("x999999");

        assertThat(one.addresses().collect(Collectors.toList())).hasSize(1);
        assertThat(one.contactPoints().collect(Collectors.toList())).hasSize(1);
        assertThat(two.addresses().collect(Collectors.toList())).hasSize(1);
        assertThat(two.contactPoints().collect(Collectors.toList())).hasSize(1);

        Address addrOne = one.addresses().findFirst().get();
        Address addrTwo = two.addresses().findFirst().get();
        assertThat(addrOne).isEqualTo(addrTwo);
        assertThat(addrOne.getAddressee()).isEqualTo("Joe Blaine");
        assertThat(addrOne.getLine1()).isEqualTo("1706 Edgewood Drive");
        assertThat(addrOne.getLine2()).isEqualTo("Apt 532");
        assertThat(addrOne.getCity()).isEqualTo("Munsford");
        assertThat(addrOne.getState()).isEqualTo("RI");
        assertThat(addrOne.getPostalCode()).isEqualTo("12345");
        assertThat(addrOne.getCountry()).isEqualTo("United States");

        assertThat(addrTwo.getAddressee()).isEqualTo("Joe Blaine");
        assertThat(addrTwo.getLine1()).isEqualTo("1706 Edgewood Drive");
        assertThat(addrTwo.getLine2()).isEqualTo("Apt 532");
        assertThat(addrTwo.getCity()).isEqualTo("Munsford");
        assertThat(addrTwo.getState()).isEqualTo("RI");
        assertThat(addrTwo.getPostalCode()).isEqualTo("12345");
        assertThat(addrTwo.getCountry()).isEqualTo("United States");

    }
}
