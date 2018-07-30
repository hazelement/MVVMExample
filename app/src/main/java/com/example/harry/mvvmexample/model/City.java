package com.example.harry.mvvmexample.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "city")
public class City {

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @PrimaryKey
    @NonNull
    private String name;
    private int current = 0;
    private double temperature = 0.0;

    public City() {
        this("", 0);
    }

    @Ignore
    public City(@NonNull String name, int tempreature) {
        this.name = name;
        this.temperature = tempreature;
    }

    @Ignore
    public City(@NonNull String name) {
        this.name = name;
    }
}
