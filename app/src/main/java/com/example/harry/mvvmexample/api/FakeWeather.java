package com.example.harry.mvvmexample.api;

import com.example.harry.mvvmexample.model.City;

public class FakeWeather {
    public interface WeatherResultCallBack{
        void onSuccess(double temp);
        void onFail();
    }

    public void getWeatherData(String cityName, final WeatherResultCallBack weatherResultCallBack ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){

                }
                weatherResultCallBack.onSuccess(Math.random());
            }
        }).start();
    }
}
