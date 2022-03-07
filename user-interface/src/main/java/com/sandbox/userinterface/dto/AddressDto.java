package com.sandbox.userinterface.dto;

import com.sandbox.services.domain.Address;

public class AddressDto {
    private String addressee;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static AddressDto from(Address address) {
       AddressDto result = new AddressDto();
       result.addressee = address.getAddressee();
       result.line1 = address.getLine1();
       result.line2 = address.getLine2();
       result.city = address.getCity();
       result.state = address.getState();
       result.postalCode = address.getPostalCode();
       result.country = address.getCountry();
       return result;
    }

    public Address toDomain() {
        return
            Address.builder()
                .addressee(this.addressee)
                .line1(this.line1)
                .line2(this.line2)
                .city(this.city)
                .state(this.state)
                .postalCode(this.postalCode)
                .country(this.country)
                .build();
    }
}
