package com.info.sehirbilgilendirme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] sehirResimleri = {
            R.drawable.istanbul1,
            R.drawable.istanbul2,
            R.drawable.istanbul3,
            R.drawable.ankara1,
            R.drawable.ankara2,
            R.drawable.ankara3,
            R.drawable.izmir1,
            R.drawable.izmir2,
            R.drawable.izmir3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnBasla = findViewById(R.id.btnBasla);

        btnBasla.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder pencere = new AlertDialog.Builder(MainActivity.this);
                pencere.setTitle("Şehir Bilgilendirme");

                CharSequence[] sehirler = {"İstanbul","Ankara","İzmir"};

                Random random = new Random();
                int rastgeleIndex = random.nextInt(sehirResimleri.length);
                pencere.setIcon(sehirResimleri[rastgeleIndex]);

                pencere.setItems(sehirler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i == 0) {
                            Intent intent = new Intent(MainActivity.this, IstanbulActivity.class);
                            startActivity(intent);
                        }
                        else if (i == 1) {
                            Intent intent = new Intent(MainActivity.this, AnkaraActivity.class);
                            startActivity(intent);
                        }
                        else if (i == 2) {
                            Intent intent = new Intent(MainActivity.this, IzmirActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                pencere.show();
            }
        });
    }
}