package com.example.harry.mvvmexample.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.harry.mvvmexample.api.FakeWeather;
import com.example.harry.mvvmexample.dao.CityDao;
import com.example.harry.mvvmexample.dao.MyDB;
import com.example.harry.mvvmexample.model.City;

import java.util.List;

public class CityRepository {

    private static String LOG_TAG = CityRepository.class.getSimpleName();

    private CityDao cityDao;
    private LiveData<List<City>> allCities;

    public CityRepository(Application application){
        MyDB db = MyDB.getDatabase(application);
        cityDao = db.cityDao();
    }

    public LiveData<City> getCity(String cityName){
        return cityDao.getCity(cityName);
    }

    public LiveData<City> getCurrentCity(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                City currentCity = cityDao.get_CurrentCity();
//                if(currentCity!=null) {
//                    Log.d(LOG_TAG, "Current city " + currentCity.getName());
//                } else {
//                    Log.d(LOG_TAG, "Current city null");
//                }
//            }
//        }).start();
        return cityDao.getCurrentCity();
    }

    public void createCity(final City city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cityDao.insert(city);
            }
        }).start();
    }

    public void updateCityStatus(final City city){
        if(city!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cityDao.insert(city);
                    cityDao.updateCurrent(city.getName(), city.getCurrent());
                }
            }).start();
        }
    }

    public void updateTemperature(final City city){
        if(city!=null) {
            FakeWeather fakeWeather = new FakeWeather();
            fakeWeather.getWeatherData(city.getName(), new FakeWeather.WeatherResultCallBack() {
                @Override
                public void onSuccess(double temp) {
                    city.setTemperature(temp);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(LOG_TAG, "Updating temperature " + city.getName() + " " + String.valueOf(city.getTemperature()));
                            cityDao.updateTemperature(city.getTemperature(), city.getName());
                        }
                    }).start();
                }

                @Override
                public void onFail() {

                }
            });
        }
    }
}
