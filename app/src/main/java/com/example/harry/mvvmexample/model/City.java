package com.example.harry.mvvmexample.model;

public class City {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTempreature() {
        return tempreature;
    }

    public void setTempreature(double tempreature) {
        this.tempreature = tempreature;
    }

    private String name;
    private double tempreature;

    public City() {
        this("", 0);
    }

    public City(String name, int tempreature) {
        this.name = name;
        this.tempreature = tempreature;
    }

    public City(String name) {
        this.name = name;
    }
}
