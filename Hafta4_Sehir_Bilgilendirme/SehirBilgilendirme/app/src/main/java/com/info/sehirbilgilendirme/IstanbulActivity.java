package com.info.sehirbilgilendirme;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IstanbulActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_istanbul);

        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);

        img1.setImageResource(R.drawable.istanbul1);
        img2.setImageResource(R.drawable.istanbul2);
        img3.setImageResource(R.drawable.istanbul3);

        txt1.setText("Ayasofya: İstanbul'un en önemli tarihi yapılarındandır.");
        txt2.setText("Sultanahmet Camii: Turistlerin en çok ziyaret ettiği yerlerden biridir.");
        txt3.setText("Galata Kulesi: İstanbul manzarası ile ünlüdür.");
    }
}