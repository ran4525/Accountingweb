package com.example.houduan.pojo;

import lombok.Data;

public class Bills {
    private String name;
    private int number;
    private String types;
    private String date;
    private String cex;

    public String getCex() {
        return cex;
    }

    public void setCex(String cex) {
        this.cex = cex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
