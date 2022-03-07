package com.sandbox.services.core;

public class CriteriaElement {
    private String name;
    private CriteriaOperation operation;
    private Object[] value;

    public CriteriaElement(String name, CriteriaOperation operation, Object value) {
        this.name = name;
        this.operation = operation;
        this.value = new Object[1];
        this.value[0] = value;
    }

    public CriteriaElement(String name, CriteriaOperation operation, Object[] values) {
        this.name = name;
        this.operation = operation;
        this.value = new Object[1];
        this.value[0] = values;
    }

    public String getName() {
        return name;
    }

    public CriteriaOperation getOperation() {
        return operation;
    }

    public Object[] getValues() {
        return value;
    }

    public Object getValue() {
        return value[0];
    }

    public static enum CriteriaOperation {
        EQUAL_TO, GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, INLIST, CONTAINS, STARTS_WITH, ENDS_WITH
    }

    public static CriteriaElement equalTo(String name, Object value) {
        return new CriteriaElement(name,CriteriaOperation.EQUAL_TO, value);
    }

    public static CriteriaElement greaterThan(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.GREATER_THAN, value);
    }

    public static CriteriaElement greaterThanOrEqual(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.GREATER_THAN_OR_EQUAL, value);
    }

    public static CriteriaElement lessThan(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.LESS_THAN, value);
    }

    public static CriteriaElement lessThanOrEqual(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.LESS_THAN_OR_EQUAL, value);
    }

    public static CriteriaElement inlist(String name, Object[] values) {
        return new CriteriaElement(name, CriteriaOperation.INLIST, values);
    }

    public static CriteriaElement contains(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.CONTAINS, value);
    }

    public static CriteriaElement startsWith(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.STARTS_WITH, value);
    }

    public static CriteriaElement endsWith(String name, Object value) {
        return new CriteriaElement(name, CriteriaOperation.ENDS_WITH, value);
    }
}
