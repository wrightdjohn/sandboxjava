package com.sandbox.services.domain;

import com.sandbox.services.core.BizId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Person implements Party {
    private BizId id;
    private String name;
    private String employeeId;
    private List<ContactPoint> contactPoints;
    private List<Address> addresses;

    private Person(BizId id, String name, String employeeId, List<ContactPoint> contactPoints, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
        this.contactPoints = contactPoints;
        this.addresses = addresses;
    }

    @Override
    public BizId getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Stream<ContactPoint> contactPoints() {
        return contactPoints.stream();
    }

    public Stream<Address> addresses() {
        return addresses.stream();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BizId id;
        private String name;
        private String employeeId;
        private List<ContactPoint> contactPoints = new ArrayList<>();
        private List<Address> addresses = new ArrayList<>();

        public Builder copyIn(Person other) {
            this.id = other.id;
            this.name = other.name;
            this.employeeId = other.employeeId;
            this.contactPoints = new ArrayList<>(other.contactPoints);
            this.addresses = new ArrayList<>(other.addresses);
            return this;
        }

        public Builder id(BizId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder address(Address address) {
            this.addresses.add(address);
            return this;
        }

        public Builder addresses(Collection<Address> addresses) {
            this.addresses.addAll(addresses);
            return this;
        }

        public Builder contactPoint(ContactPoint contactPoint) {
            this.contactPoints.add(contactPoint);
            return this;
        }

        public Builder contactPoints(Collection<ContactPoint> contactPoints) {
            this.contactPoints.addAll(contactPoints);
            return this;
        }

        public Person build() {
            return new Person(this.id,this.name,this.employeeId,this.contactPoints,this.addresses);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(employeeId, person.employeeId) &&
                contactPointsEqual(person) &&
                addressesEqual(person);
    }

    private boolean addressesEqual(Person person) {
        if (addresses == null && person.addresses == null) return true;
        if (addresses == null || person.addresses == null) return false;
        if (!addresses.containsAll(person.addresses)) return false;
        return person.addresses.containsAll(addresses);
    }

    private boolean contactPointsEqual(Person person) {
        if (contactPoints == null && person.contactPoints == null) return true;
        if (contactPoints == null || person.contactPoints == null) return false;
        if (!contactPoints.containsAll(person.contactPoints)) return false;
        return person.contactPoints.containsAll(contactPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employeeId, contactPoints, addresses);
    }
}
