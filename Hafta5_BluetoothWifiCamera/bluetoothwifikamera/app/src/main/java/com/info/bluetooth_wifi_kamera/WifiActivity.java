package com.info.bluetooth_wifi_kamera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WifiActivity extends AppCompatActivity {

    Button btnWifiAyar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        btnWifiAyar = findViewById(R.id.btnWifiAyar);

        btnWifiAyar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cihazın Android sürümü 10 (API 29) veya daha yeniyse
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivity(panelIntent);
                } else {
                    // Daha eski cihazlar için direkt genel Wi-Fi ayarlarına yönlendir
                    Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(settingsIntent);
                    Toast.makeText(WifiActivity.this, "Wi-Fi ayarları açılıyor...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}