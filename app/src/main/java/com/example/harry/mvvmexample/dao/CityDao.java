package com.example.harry.mvvmexample.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.harry.mvvmexample.model.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(City city);

    @Query("UPDATE city SET temperature = :temperature WHERE name = :name")
    void updateTemperature(double temperature, String name);

    @Query("UPDATE city SET current = :current WHERE name = :name")
    void updateCurrent(String name, int current);

    @Query("DELETE FROM city")
    void deleteAll();

    @Query("SELECT * FROM city")
    LiveData<List<City>> getAllCity();

    @Query("SELECT * FROM city WHERE name= :cityName")
    LiveData<City> getCity(String cityName);

    @Query("SELECT * FROM city WHERE current = 1")
    LiveData<City> getCurrentCity();

    @Query("SELECT * FROM city WHERE current = 1")
    City get_CurrentCity();
}
