package com.example.margingmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Operator {

    @JsonProperty("+")
    PLUS ("+"),
    @JsonProperty("-")
    MINUS ("-"),
    @JsonProperty("*")
    STAR ("*");

    private final String operator;

    Operator(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }
    public static Operator fromString(String opString) {
        for (Operator op : Operator.values()) {
            if (op.operator.equalsIgnoreCase(opString)) {
                return op;
            }
        }
        return null;
    }
}
