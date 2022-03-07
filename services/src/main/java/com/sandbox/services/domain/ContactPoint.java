package com.sandbox.services.domain;

import java.util.Objects;

public class ContactPoint {
    private ContactPointType type;
    private String userName;
    private boolean authenticated;

    public ContactPoint(ContactPointType type, String userName, boolean authenticated) {
        this.type = type;
        this.userName = userName;
        this.authenticated = authenticated;
    }

    public ContactPointType getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public static enum ContactPointType {
        PHONE,EMAIL,FACEBOOK,TWITTER,LINKEDIN,GITHUB
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPoint that = (ContactPoint) o;
        return authenticated == that.authenticated && type == that.type && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, userName, authenticated);
    }
}
