package com.info.haritaservisi;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_current_location).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CurrentLocationActivity.class));
        });

        findViewById(R.id.btn_set_location).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SetLocationActivity.class));
        });
    }
}