package com.example.kiosk;

public class Promotion {
    private int id;
    private String name;
    private String detail;

    public Promotion(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }
}
