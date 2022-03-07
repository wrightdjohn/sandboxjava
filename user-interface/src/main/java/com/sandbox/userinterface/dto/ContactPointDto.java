package com.sandbox.userinterface.dto;

import com.sandbox.services.domain.ContactPoint;

public class ContactPointDto {
    private String type;
    private String userName;
    private boolean authenticated;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public static ContactPointDto from(ContactPoint contactPoint) {
        ContactPointDto result = new ContactPointDto();
        result.type = contactPoint.getType().name();
        result.authenticated = contactPoint.isAuthenticated();
        result.userName = contactPoint.getUserName();
        return result;
    }

    public ContactPoint toDomain() {
        return
           new ContactPoint(ContactPoint.ContactPointType.valueOf(this.type), this.userName, this.authenticated );
    }
}
