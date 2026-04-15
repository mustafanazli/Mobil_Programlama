package com.info.bluetooth_wifi_kamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    Button btnBtAc, btnBtKapat, btnGorunurOl, btnListele;
    ListView cihazListesi;
    BluetoothAdapter bluetoothAdapter;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btnBtAc = findViewById(R.id.btnBtAc);
        btnBtKapat = findViewById(R.id.btnBtKapat);
        btnGorunurOl = findViewById(R.id.btnGorunurOl);
        btnListele = findViewById(R.id.btnListele);
        cihazListesi = findViewById(R.id.cihazListesi);

        // Android 12 (API 31) ve sonrası için Güvenlik/İzin Kontrolü (ÇÖKMEYİ ÖNLER)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // Kullanıcıdan ekranda onay kutusu ile izin istiyoruz
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                }, 100);
            }
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Cihazınızda Bluetooth bulunmuyor!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnBtAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                } else {
                    Toast.makeText(BluetoothActivity.this, "Bluetooth zaten açık!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBtKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    Toast.makeText(BluetoothActivity.this, "Bluetooth kapatıldı.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGorunurOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                startActivity(discoverableIntent);
            }
        });

        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                    ArrayList<String> cihazIsimleri = new ArrayList<>();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            cihazIsimleri.add(device.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BluetoothActivity.this, R.layout.list_item, cihazIsimleri);
                        cihazListesi.setAdapter(adapter);
                        Toast.makeText(BluetoothActivity.this, "Cihazlar listelendi.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BluetoothActivity.this, "Eşleşmiş cihaz bulunamadı.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BluetoothActivity.this, "Önce Bluetooth'u açmalısınız!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}