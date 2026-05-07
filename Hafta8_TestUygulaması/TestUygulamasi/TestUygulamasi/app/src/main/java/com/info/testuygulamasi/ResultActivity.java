package com.info.testuygulamasi;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvPuan = findViewById(R.id.tvPuan);
        Button btnAnaEkranaDon = findViewById(R.id.btnAnaEkranaDon);

        // QuizActivity'den gönderilen puanı al
        int puan = getIntent().getIntExtra("PUAN", 0);
        tvPuan.setText("Alınan Puan: " + puan);

        btnAnaEkranaDon.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Geri dönüldüğünde önceki ekranları temizler
            startActivity(intent);
            finish();
        });
    }
}