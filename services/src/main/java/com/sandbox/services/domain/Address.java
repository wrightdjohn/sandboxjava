package com.sandbox.services.domain;

import java.util.Objects;

public class Address {
    private String addressee;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private Address() {
    }

    public String getAddressee() {
        return addressee;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String addressee;
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String postalCode;
        private String country = "United States";

        public Builder copyIn(Address other) {
            this.addressee = other.addressee;
            this.line1 = other.line1;
            this.line2 = other.line2;
            this.city = other.city;
            this.state = other.state;
            this.postalCode = other.postalCode;
            this.country = other.country;
            return this;
        }

        public Builder addressee(String addressee) {
           this.addressee = addressee;
           return this;
        }

        public Builder line1(String line1) {
            this.line1 = line1;
            return this;
        }

        public Builder line2(String line2) {
            this.line2 = line2;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Address build() {
            Address instance = new Address() ;
            instance.addressee = this.addressee;
            instance.line1 = this.line1;
            instance.line2 = this.line2;
            instance.city = this.city;
            instance.state = this.state;
            instance.postalCode = this.postalCode;
            instance.country = this.country;
            return instance;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressee, address.addressee) && Objects.equals(line1, address.line1) && Objects.equals(line2, address.line2) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(postalCode, address.postalCode) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressee, line1, line2, city, state, postalCode, country);
    }
}
