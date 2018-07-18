package com.example.harry.mvvmexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edtCity)
    EditText edtCity;

    @BindView(R.id.txtViewTemperature)
    TextView textViewTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
