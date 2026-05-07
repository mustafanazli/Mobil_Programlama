package com.info.testuygulamasi;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    TextView tvSoru;
    RadioGroup radioGroupSecenekler;
    RadioButton rbA, rbB, rbC, rbD, rbE;
    Button btnCevapla;

    private Veritabani v1;
    private ArrayList<SoruModel> soruListesi = new ArrayList<>();
    private int gecerliSoruIndex = 0;
    private int toplamPuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvSoru = findViewById(R.id.tvSoru);
        radioGroupSecenekler = findViewById(R.id.radioGroupSecenekler);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);
        rbE = findViewById(R.id.rbE);
        btnCevapla = findViewById(R.id.btnCevapla);

        v1 = new Veritabani(this);
        SorulariGetir();

        btnCevapla.setOnClickListener(view -> {
            int selectedId = radioGroupSecenekler.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Lütfen bir seçenek işaretleyin", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton secilenRb = findViewById(selectedId);
            String secilenCevapHarfi = secilenRb.getText().toString().substring(0, 1); // A, B, C...

            if (secilenCevapHarfi.equalsIgnoreCase(soruListesi.get(gecerliSoruIndex).dogruCevap)) {
                toplamPuan += 20;
            }

            gecerliSoruIndex++;
            radioGroupSecenekler.clearCheck();

            if (gecerliSoruIndex < 5 && gecerliSoruIndex < soruListesi.size()) {
                SoruGoster();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("PUAN", toplamPuan);
                startActivity(intent);
                finish();
            }
        });
    }

    private void SorulariGetir() {
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sorular LIMIT 5", null);

        while (cursor.moveToNext()) {
            SoruModel soru = new SoruModel();
            soru.soruMetni = cursor.getString(0);
            soru.secA = cursor.getString(1);
            soru.secB = cursor.getString(2);
            soru.secC = cursor.getString(3);
            soru.secD = cursor.getString(4);
            soru.secE = cursor.getString(5);
            soru.dogruCevap = cursor.getString(6);
            soruListesi.add(soru);
        }
        cursor.close();
        db.close();

        if (soruListesi.size() > 0) {
            SoruGoster();
        } else {
            Toast.makeText(this, "Veritabanında soru yok!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void SoruGoster() {
        SoruModel s = soruListesi.get(gecerliSoruIndex);
        tvSoru.setText((gecerliSoruIndex + 1) + ". " + s.soruMetni);
        rbA.setText("A) " + s.secA);
        rbB.setText("B) " + s.secB);
        rbC.setText("C) " + s.secC);
        rbD.setText("D) " + s.secD);
        rbE.setText("E) " + s.secE);
    }

    // Basit veri modeli sınıfı (Inner class olarak eklendi)
    class SoruModel {
        String soruMetni, secA, secB, secC, secD, secE, dogruCevap;
    }
}