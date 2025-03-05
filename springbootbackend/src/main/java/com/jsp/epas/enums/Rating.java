package com.jsp.epas.enums;

public enum Rating {
    A("Outstanding"),
    B("Very Good"),
    C("Good"),
    D("Need to Improve"),
    E("Low Performer");

    private final String description;

    Rating(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
