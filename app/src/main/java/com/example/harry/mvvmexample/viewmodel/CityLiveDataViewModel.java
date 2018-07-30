package com.example.harry.mvvmexample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.harry.mvvmexample.model.City;
import com.example.harry.mvvmexample.repo.CityRepository;

import java.util.List;

public class CityLiveDataViewModel extends AndroidViewModel {

    private CityRepository cityRepository;

    private static String LOG_TAG = ViewModel.class.getSimpleName();

    private LiveData<City> city;

    public CityLiveDataViewModel(Application application) {

        super(application);
        cityRepository = new CityRepository(application);
        city = cityRepository.getCurrentCity();

        Log.d(LOG_TAG, "ViewModel created.");
    }

    public LiveData<City> getCity() {
        return city;
    }

    public LiveData<List<City>> getAllCities(){
        return cityRepository.getAllCities();
    }

    public void createCity(String cityName){
        City newCity = new City(cityName);
        cityRepository.createCity(newCity);
    }

    public void setCurrentCity(final String cityName){
        City newCity = new City(cityName);
        newCity.setCurrent(1);
        cityRepository.updateCityStatus(newCity);
    }

    public void setNotCurrentCity(final String cityName){
        City newCity = new City(cityName);
        newCity.setCurrent(0);
        cityRepository.updateCityStatus(newCity);
    }

    public void updateTemperature(String cityName){
        cityRepository.updateTemperature(new City(cityName));
    }


}
