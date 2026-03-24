package com.info.sehir_bilgilendirme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnBasla;
    LinearLayout layoutPanel;
    ImageView imgRastgele;

    TextView txtIstanbul, txtAnkara, txtIzmir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBasla = findViewById(R.id.btnBasla);
        layoutPanel = findViewById(R.id.layoutPanel);
        imgRastgele = findViewById(R.id.imgRastgele);

        txtIstanbul = findViewById(R.id.txtIstanbul);
        txtAnkara = findViewById(R.id.txtAnkara);
        txtIzmir = findViewById(R.id.txtIzmir);

        btnBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layoutPanel.getVisibility() == View.GONE) {
                    layoutPanel.setVisibility(View.VISIBLE);
                }

                int[] resimler = {
                        R.drawable.ist1, R.drawable.ist2, R.drawable.ist3,
                        R.drawable.ank1, R.drawable.ank2, R.drawable.ank3,
                        R.drawable.izm1, R.drawable.izm2, R.drawable.izm3
                };

                Random random = new Random();
                int index = random.nextInt(resimler.length);

                imgRastgele.setImageResource(resimler[index]);
            }
        });

        // İstanbul
        txtIstanbul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IstanbulActivity.class));
            }
        });

        // Ankara
        txtAnkara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AnkaraActivity.class));
            }
        });

        // İzmir
        txtIzmir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IzmirActivity.class));
            }
        });
    }
}