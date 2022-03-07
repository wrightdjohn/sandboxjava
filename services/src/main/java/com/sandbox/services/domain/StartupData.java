package com.sandbox.services.domain;

import com.sandbox.services.core.StringBizId;
import java.util.Arrays;
import java.util.List;

public class StartupData {
    public static Address mainOffice =
            Address.builder()
                    .line1("716 Elm Street")
                    .line2("Apt 201")
                    .city("Monroeville")
                    .state("Va")
                    .postalCode("55551")
                    .country("United States")
                    .build();

    public static Address nycOffice =
            Address.builder()
                    .addressee("Joe Black")
                    .line1("1616 38th Avenue")
                    .line2("Apt 201")
                    .city("New York")
                    .state("NY")
                    .postalCode("40404")
                    .country("United States")
                    .build();

    public static Person admin =
            Person.builder()
                    .id(new StringBizId("6220f65C8f03315fb10b327c"))
                    .name("Administrator")
                    .employeeId("admin")
                    .build();

    public static Person joeBlack =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0a"))
                    .name("Joseph R. Black")
                    .employeeId("x121212")
                    .address(Address.builder().copyIn(mainOffice).addressee("Joe Black").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "616-555-1212", true))
                    .build();

    public static Person jimBorman =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0b"))
                    .name("James L. Borman")
                    .employeeId("x131313")
                    .address(Address.builder().copyIn(mainOffice).addressee("Jim Borman").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "616-555-2282", true))
                    .build();

    public static Person jessicaWilde =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0c"))
                    .name("Jessica Laverne Wilde")
                    .employeeId("x232323")
                    .address(Address.builder().copyIn(mainOffice).addressee("Jessica Wilde").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "616-555-3452", true))
                    .build();

    public static Person martinLake =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0d"))
                    .name("Martin A. Lake")
                    .employeeId("y100121")
                    .address(Address.builder().copyIn(nycOffice).addressee("Martin Lake").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "716-555-4112", true))
                    .build();

    public static Person virginiaOToole =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0e"))
                    .name("Virginia O'Toole")
                    .employeeId("y221134")
                    .address(Address.builder().copyIn(nycOffice).addressee("Ginni O'Toole").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "716-555-4422", true))
                    .build();

    public static Person preetaSmith =
            Person.builder()
                    .id(new StringBizId("6220fc388c70850875b5af0f"))
                    .name("Preeta Smith")
                    .employeeId("y323554")
                    .address(Address.builder().copyIn(mainOffice).addressee("Preeta Smith").build())
                    .address(Address.builder().copyIn(nycOffice).addressee("Preeta Smith").build())
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.PHONE, "716-555-5423", true))
                    .contactPoint(new ContactPoint(ContactPoint.ContactPointType.EMAIL, "preeta.smith@bigbiz.com", true))
                    .build();

    public static List<Person> personList() {
        return Arrays.asList(admin,joeBlack,jimBorman,jessicaWilde,martinLake,virginiaOToole,preetaSmith);
    }
}
