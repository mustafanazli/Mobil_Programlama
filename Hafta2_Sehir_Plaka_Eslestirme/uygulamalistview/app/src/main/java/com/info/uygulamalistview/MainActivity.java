package com.info.uygulamalistview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView listViewNumbers, listViewCities;
    Button btnGenerate;
    ArrayList<Integer> randomNumbers = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();
    ArrayAdapter<Integer> numberAdapter;
    ArrayAdapter<String> cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNumbers = findViewById(R.id.listViewNumbers);
        listViewCities = findViewById(R.id.listViewCities);
        btnGenerate = findViewById(R.id.btnGenerate);

        String[] ilList = {"Adana","Adıyaman","Afyon","Ağrı","Amasya","Ankara","Antalya",
                "Artvin","Aydın","Balıkesir","Bilecik","Bingöl","Bitlis","Bolu","Burdur",
                "Bursa","Çanakkale","Çankırı","Çorum","Denizli","Diyarbakır","Edirne",
                "Elazığ","Erzincan","Erzurum","Eskişehir","Gaziantep","Giresun","Gümüşhane",
                "Hakkari","Hatay","Isparta","Mersin","İstanbul","İzmir","Kars","Kastamonu",
                "Kayseri","Kırklareli","Kırşehir","Kocaeli","Konya","Kütahya","Malatya",
                "Manisa","Kahramanmaraş","Mardin","Muğla","Muş","Nevşehir","Niğde","Ordu",
                "Rize","Sakarya","Samsun","Siirt","Sinop","Sivas","Tekirdağ","Tokat",
                "Trabzon","Tunceli","Şanlıurfa","Uşak","Van","Yozgat","Zonguldak",
                "Aksaray","Bayburt","Karaman","Kırıkkale","Batman","Şırnak","Bartın",
                "Ardahan","Iğdır","Yalova","Karabük","Kilis","Osmaniye","Düzce"};

        Collections.addAll(cities, ilList);

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        listViewCities.setAdapter(cityAdapter);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumbers();
            }
        });

        generateRandomNumbers();

        listViewCities.setOnItemClickListener((parent, view, position, id) -> {
            int generatedPlate = randomNumbers.get(position);
            String selectedCity = cities.get(position);

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("generatedPlate", generatedPlate);
            intent.putExtra("position", position);
            intent.putExtra("cityName", selectedCity);
            startActivity(intent);
        });
    }

    private void generateRandomNumbers() {
        randomNumbers.clear();
        for (int i = 1; i <= 81; i++) {
            randomNumbers.add(i);
        }
        Collections.shuffle(randomNumbers);

        numberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, randomNumbers);
        listViewNumbers.setAdapter(numberAdapter);
    }
}