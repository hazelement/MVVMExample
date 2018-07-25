package com.example.harry.mvvmexample.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.harry.mvvmexample.model.City;

public class CityLiveDataViewModelFactory implements ViewModelProvider.Factory {

    private City city;

    public CityLiveDataViewModelFactory(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CityLiveDataViewModel.class)){
            return (T) new CityLiveDataViewModel(city);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
