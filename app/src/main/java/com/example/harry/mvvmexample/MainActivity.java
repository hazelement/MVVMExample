/**
 * An android project to demo the use of architecture components
 *
 * The app allow user to input city name and get weather from yahoo
 * When app hide in background should demonstrate viewmodel live through activity life cycle
 * WHen app is closed/reopened, Room should be used to demo persistent storage/caching
 * TODO setup data binding
 */

package com.example.harry.mvvmexample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harry.mvvmexample.model.City;
import com.example.harry.mvvmexample.viewmodel.CityLiveDataViewModel;

import java.util.List;

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

    @BindView(R.id.rvCityList)
    RecyclerView rvCityList;

    private CityLiveDataViewModel cityLiveDataViewModel;
    private RecyclerView.Adapter mAdapter;

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

        rvCityList.setLayoutManager(new LinearLayoutManager(this));
        rvCityList.setAdapter(mAdapter);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        TextView temperature;

        public MyViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.vhCityName);
            temperature = itemView.findViewById(R.id.vhTemperature);
        }
    }

    private void subscribeObserver(){
        Log.d(LOGTAG, "Subscribe observer");
        cityLiveDataViewModel.getCity().observe(this, cityObserver);

        cityLiveDataViewModel.getAllCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable final List<City> cities) {
                Log.d(LOGTAG, "city number changed");
                mAdapter = new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter_view_holder, parent, false);
                        return new MyViewHolder(v);
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        try {
                            MyViewHolder viewHolder = (MyViewHolder) holder;
                            viewHolder.cityName.setText(cities.get(position).getName());
                            viewHolder.temperature.setText(String.valueOf(cities.get(position).getTemperature()));
                        } catch (NullPointerException e){

                        }
                    }

                    @Override
                    public int getItemCount() {
                        if (cities != null) {
                            return cities.size();
                        } else {
                            return 0;
                        }
                    }
                };
                rvCityList.setAdapter(mAdapter);
            }
        });
    }
}
