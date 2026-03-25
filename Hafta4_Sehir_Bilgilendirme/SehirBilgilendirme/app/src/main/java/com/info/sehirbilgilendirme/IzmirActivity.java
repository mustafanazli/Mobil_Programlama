package com.info.sehirbilgilendirme;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class IzmirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_izmir);

        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);

        img1.setImageResource(R.drawable.izmir1);
        img2.setImageResource(R.drawable.izmir2);
        img3.setImageResource(R.drawable.izmir3);

        txt1.setText("Saat Kulesi: İzmir'in en önemli simgelerindendir.");
        txt2.setText("Kordon: Deniz manzarası ile ünlü yürüyüş alanıdır.");
        txt3.setText("Efes Antik Kenti: Tarihi ve turistik açıdan çok önemli bir yerdir.");
    }
}