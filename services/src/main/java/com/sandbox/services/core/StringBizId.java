package com.sandbox.services.core;

import java.util.Objects;

public class StringBizId implements BizId {
    private String value;

    public StringBizId(String value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return value;
    }

    @Override
    public Object asRawValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof BizId)) return false;
        BizId that = (BizId) o;
        return value.equals(that.asText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
