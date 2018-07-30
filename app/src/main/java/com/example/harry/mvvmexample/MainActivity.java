/**
 * An android project to demo the use of architecture components
 *
 * The app allow user to input city name and get weather from yahoo
 * When app hide in background should demonstrate viewmodel live through activity life cycle
 * WHen app is closed/reopened, Room should be used to demo persistent storage/caching
 * TODO setup DAO
 * TODO setup data binding
 */

package com.example.harry.mvvmexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harry.mvvmexample.model.City;
import com.example.harry.mvvmexample.viewmodel.CityLiveDataViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static String LOGTAG = MainActivity.class.getSimpleName();

    @BindView(R.id.edtCity)
    EditText edtCity;

    @BindView(R.id.txtViewTemperature)
    TextView textViewTemperature;

    @BindView(R.id.txtCityName)
    TextView txtCityName;

    private CityLiveDataViewModel cityLiveDataViewModel;

    private final Observer<City> cityObserver = new Observer<City>() {
        @Override
        public void onChanged(@Nullable City city) {
            Log.d(LOGTAG, "Observed change");
            if(city != null) {
                updateCityUI(city);
            } else {
                String cityName = edtCity.getText().toString();
                Log.d(LOGTAG, "Observed null data, creating new city.");
                cityLiveDataViewModel.createCity(cityName);
                cityLiveDataViewModel.updateTemperature(cityName);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cityLiveDataViewModel = ViewModelProviders.of(this).get(CityLiveDataViewModel.class);
        subscribeObserver();
    }

    @OnClick(R.id.btnUpdate)
    public void onClickUpdate(){
        Log.d(LOGTAG, "Update button");
        String oldCity = txtCityName.getText().toString();
        String newCity = edtCity.getText().toString();

        cityLiveDataViewModel.setNotCurrentCity(oldCity);
        cityLiveDataViewModel.setCurrentCity(newCity);
        cityLiveDataViewModel.updateTemperature(newCity);
    }

    private void updateCityUI(City city){
        Log.d(LOGTAG, "Updating UI");

        textViewTemperature.setText(String.valueOf(city.getTemperature()));
        txtCityName.setText(city.getName());
    }


    private void subscribeObserver(){
        Log.d(LOGTAG, "Subscribe observer");
        cityLiveDataViewModel.getCity().observe(this, cityObserver);
    }
}
