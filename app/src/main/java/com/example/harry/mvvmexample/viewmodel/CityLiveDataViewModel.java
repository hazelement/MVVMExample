package com.example.harry.mvvmexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.harry.mvvmexample.api.FakeWeather;
import com.example.harry.mvvmexample.model.City;

public class CityLiveDataViewModel extends ViewModel {

    private static String LOG_TAG = ViewModel.class.getSimpleName();

    private City city;

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Double> temperature = new MutableLiveData<Double>();

    public CityLiveDataViewModel(City city) {
        this.city = city;
        updateData();
    }

    public void updateData(){
        CityLiveDataViewModel.this.name.postValue(city.getName());
        CityLiveDataViewModel.this.temperature.postValue(city.getTempreature());
    }

    private void updateTemperature(){
        FakeWeather fakeWeather = new FakeWeather();
        fakeWeather.getWeatherData(city.getName(), new FakeWeather.WeatherResultCallBack() {
            @Override
            public void onSuccess(double temp) {
                city.setTempreature(temp);
                updateData();
            }
            @Override
            public void onFail() {

            }
        });
    }

    public LiveData<String> getCityName(){
        return name;
    }

    public LiveData<Double> getTemperature(){
        return temperature;
    }

    public void updateCity(String cityName){
        this.city.setName(cityName);
        updateTemperature();
    }

}
