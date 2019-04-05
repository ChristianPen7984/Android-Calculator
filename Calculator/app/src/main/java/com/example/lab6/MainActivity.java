package com.example.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ResultCommunicator {

    Result resulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resulta = (Result)getSupportFragmentManager().findFragmentById(R.id.result);
    }

    @Override
    public void setResult(String result) {
        resulta.setData(result);
    }
}
