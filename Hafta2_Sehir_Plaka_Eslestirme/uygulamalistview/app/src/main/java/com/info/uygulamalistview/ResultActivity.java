package com.info.uygulamalistview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textViewResult;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewResult = findViewById(R.id.textViewResult);
        btnBack = findViewById(R.id.btnBack);

        int generatedPlate = getIntent().getIntExtra("generatedPlate", 0);
        int position = getIntent().getIntExtra("position", -1);
        String cityName = getIntent().getStringExtra("cityName");

        int realPlate = position + 1;
        if (generatedPlate == realPlate) {
            textViewResult.setText(cityName + " ile plaka eşleşti!");
        } else {
            textViewResult.setText(cityName + " ile plaka eşleşmedi!");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}