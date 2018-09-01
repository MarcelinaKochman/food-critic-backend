package com.wfiis.model;

public enum Rate {
    HATE(1, "I hate it!"),
    TO_IMPROVE(2, "Needs to improve!"),
    OK(3, "It was OK!"),
    GOOD(4, "Really good place!"),
    EXCELENT(5, "Outstanding!");

    private int value;
    private String description;

    Rate(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
