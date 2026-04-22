package com.info.sensoruygulamasi;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThermometerSensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mSensor;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermometer_sensor); // Her sensör için kendi layout'u

        textView = findViewById(R.id.sensor_data_text);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // DİKKAT: Diğer dosyalarda TYPE_PRESSURE kısmını ilgili sensörle değiştir
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (mSensor == null) {
            textView.setText("Bu sensör cihazınızda bulunmamaktadır.");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Veriyi ekrana yazdır
        textView.setText("Thermometer: " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensor != null)
            sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}