package com.info.testuygulamasi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText eSoru, eA, eB, eC, eD, eE, eDogru;
    Button btnEkle, btnGuncelle, btnSil, btnSinavaBasla;
    ListView listViewSorular;

    private Veritabani v1;
    private ArrayList<String> soruListesi = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String secilenSoru = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = new Veritabani(this);

        eSoru = findViewById(R.id.editSoru);
        eA = findViewById(R.id.editA);
        eB = findViewById(R.id.editB);
        eC = findViewById(R.id.editC);
        eD = findViewById(R.id.editD);
        eE = findViewById(R.id.editE);
        eDogru = findViewById(R.id.editDogru);

        btnEkle = findViewById(R.id.btnEkle);
        btnGuncelle = findViewById(R.id.btnGuncelle);
        btnSil = findViewById(R.id.btnSil);
        btnSinavaBasla = findViewById(R.id.btnSinavaBasla);
        listViewSorular = findViewById(R.id.listViewSorular);

        adapter = new ArrayAdapter<>(this, R.layout.list_item_soru, soruListesi);
        listViewSorular.setAdapter(adapter);

        SorulariListele();

        btnEkle.setOnClickListener(view -> KayitEkle());
        btnGuncelle.setOnClickListener(view -> KayitGuncelle());
        btnSil.setOnClickListener(view -> KayitSil());

        btnSinavaBasla.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        listViewSorular.setOnItemClickListener((parent, view, position, id) -> {
            secilenSoru = soruListesi.get(position);
            SoruDetaylariniGetir(secilenSoru);
        });
    }

    private void SorulariListele() {
        soruListesi.clear();
        soruListesi.addAll(v1.tumSorulariGetir()); // YENİ: Tek satırda veritabanından çek
        adapter.notifyDataSetChanged();
    }

    private void SoruDetaylariniGetir(String soruMetni) {
        Cursor cursor = v1.soruDetayGetir(soruMetni); // YENİ: Veritabanı metodunu çağır
        if (cursor.moveToFirst()) {
            eSoru.setText(cursor.getString(0));
            eA.setText(cursor.getString(1));
            eB.setText(cursor.getString(2));
            eC.setText(cursor.getString(3));
            eD.setText(cursor.getString(4));
            eE.setText(cursor.getString(5));
            eDogru.setText(cursor.getString(6));
        }
        cursor.close();
    }

    private void KayitEkle() {
        if (eSoru.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Soru metni boş olamaz!", Toast.LENGTH_SHORT).show();
            return;
        }

        // YENİ: Sadece metodu çağırıyoruz, tüm işi Veritabani sınıfı yapıyor
        boolean basarili = v1.soruEkle(
                eSoru.getText().toString(), eA.getText().toString(),
                eB.getText().toString(), eC.getText().toString(),
                eD.getText().toString(), eE.getText().toString(), eDogru.getText().toString()
        );

        if (basarili) {
            Toast.makeText(this, "Soru Eklendi", Toast.LENGTH_SHORT).show();
            AlanlariTemizle();
            SorulariListele();
        } else {
            Toast.makeText(this, "Bu soru zaten var veya hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    private void KayitSil() {
        if (secilenSoru.isEmpty()) {
            Toast.makeText(this, "Lütfen silinecek soruyu listeden seçin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (v1.soruSil(secilenSoru)) {
            Toast.makeText(this, "Soru Silindi", Toast.LENGTH_SHORT).show();
            AlanlariTemizle();
            SorulariListele();
        } else {
            Toast.makeText(this, "Silme Hatası", Toast.LENGTH_SHORT).show();
        }
    }

    private void KayitGuncelle() {
        if (secilenSoru.isEmpty()) {
            Toast.makeText(this, "Lütfen güncellenecek soruyu listeden seçin", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean basarili = v1.soruGuncelle(
                secilenSoru, eSoru.getText().toString(),
                eA.getText().toString(), eB.getText().toString(),
                eC.getText().toString(), eD.getText().toString(),
                eE.getText().toString(), eDogru.getText().toString()
        );

        if (basarili) {
            Toast.makeText(this, "Soru Güncellendi", Toast.LENGTH_SHORT).show();
            AlanlariTemizle();
            SorulariListele();
        } else {
            Toast.makeText(this, "Güncelleme Hatası", Toast.LENGTH_SHORT).show();
        }
    }

    private void AlanlariTemizle() {
        eSoru.setText("");
        eA.setText("");
        eB.setText("");
        eC.setText("");
        eD.setText("");
        eE.setText("");
        eDogru.setText("");
        secilenSoru = "";
    }
}