package com.ingemark.springdemo.model.enums;

public enum Degree {
    UNDERGRADUATE(180),
    GRADUATE(300),
    POSTGRADUATE(500);

    private final int minEcts;

    Degree(int minEcts) {
        this.minEcts = minEcts;
    }

    public int getMinEcts() {
        return minEcts;
    }
}
