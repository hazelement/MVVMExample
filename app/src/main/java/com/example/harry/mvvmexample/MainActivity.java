/**
 * An android project to demo the use of architecture components
 *
 * The app allow user to input city name and get weather from yahoo
 * When app hide in background should demonstrate viewmodel live through activity life cycle
 * WHen app is closed/reopened, Room should be used to demo persistent storage/caching
 */

package com.example.harry.mvvmexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harry.mvvmexample.model.City;
import com.example.harry.mvvmexample.viewmodel.CityLiveDataViewModel;
import com.example.harry.mvvmexample.viewmodel.CityLiveDataViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edtCity)
    EditText edtCity;

    @BindView(R.id.txtViewTemperature)
    TextView textViewTemperature;

    private CityLiveDataViewModel cityLiveDataViewModel;

    private final Observer<Double> cityTempObserver = new Observer<Double>() {
        @Override
        public void onChanged(@Nullable Double value) {
            updateCityTemperature(value);
        }
    };

    private final Observer<String> cityNameObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            updateCityName(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City city = new City();
        CityLiveDataViewModelFactory cityLiveDataViewModelFactory = new CityLiveDataViewModelFactory(city);
        cityLiveDataViewModel = ViewModelProviders.of(this, cityLiveDataViewModelFactory).get(CityLiveDataViewModel.class);
        ButterKnife.bind(this);

        subscribeObserver();
    }

    @OnClick(R.id.btnUpdate)
    public void onClickUpdate(){
        String newCity = edtCity.getText().toString();
        updateCityTemperature((double)0);
        cityLiveDataViewModel.updateCity(newCity);
    }


    private void updateCityTemperature(Double temp){
        textViewTemperature.setText(String.valueOf(temp));
    }

    private void updateCityName(String name){
        edtCity.setText(name);
    }

    private void subscribeObserver(){
        cityLiveDataViewModel.getTemperature().observe(this, cityTempObserver);
        cityLiveDataViewModel.getCityName().observe(this, cityNameObserver);
    }
}
