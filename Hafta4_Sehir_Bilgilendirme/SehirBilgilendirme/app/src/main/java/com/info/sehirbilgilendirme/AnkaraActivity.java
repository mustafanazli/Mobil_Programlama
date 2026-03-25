package com.info.sehirbilgilendirme;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AnkaraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ankara);

        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);

        img1.setImageResource(R.drawable.ankara1);
        img2.setImageResource(R.drawable.ankara2);
        img3.setImageResource(R.drawable.ankara3);

        txt1.setText("Anıtkabir: Mustafa Kemal Atatürk'ün anıt mezarıdır.");
        txt2.setText("Ankara Kalesi: Şehrin tarihi dokusunu yansıtan önemli bir yapıdır.");
        txt3.setText("Hamamönü: Restore edilmiş tarihi evleri ile ünlüdür.");
    }
}