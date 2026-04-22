package com.info.sensoruygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buton tanımlamaları ve Dinleyiciler
        setupButton(R.id.sensor1_button, AccelerometerSensorActivity.class);
        setupButton(R.id.sensor2_button, CompassSensorActivity.class);
        setupButton(R.id.sensor3_button, GyroscopeSensorActivity.class);
        setupButton(R.id.sensor4_button, HumiditySensorActivity.class);
        setupButton(R.id.sensor5_button, LightSensorActivity.class);
        setupButton(R.id.sensor6_button, MagnetometerSensorActivity.class);
        setupButton(R.id.sensor7_button, PressureSensorActivity.class);
        setupButton(R.id.sensor8_button, ProximitySensorActivity.class);
        setupButton(R.id.sensor9_button, ThermometerSensorActivity.class);
    }

    private void setupButton(int id, Class<?> activityClass) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activityClass)));
    }
}