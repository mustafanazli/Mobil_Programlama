package com.info.dovizuygulamasi; // Kendi paket adınla (package) değiştirmeyi unutma

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.info.dovizuygulamasi.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView chfText, usdText, jpyText, tryText, cadText;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chfText = findViewById(R.id.chfText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        Button button = findViewById(R.id.button);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRates();
            }
        });
    }

    public void getRates() {
        // Senin yeni API anahtarınla güncellenmiş URL [cite: 8, 278]
        String apiUrl = "http://data.fixer.io/api/latest?access_key=335ba8879905f77a93d9416b639a6b46";

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final String result = downloadData(apiUrl);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(result);
                    }
                });
            }
        });
    }

    private String downloadData(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            Log.e("DownloadData", "Bağlantı hatası: " + e.getMessage());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void updateUI(String jsonString) {
        if (jsonString == null) {
            Toast.makeText(this, "İnternet bağlantısını veya API key'i kontrol edin", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // API'den hata dönüp dönmediğini kontrol ediyoruz
            if (!jsonObject.getBoolean("success")) {
                Toast.makeText(this, "API Hatası! Key geçersiz olabilir.", Toast.LENGTH_LONG).show();
                return;
            }

            JSONObject rates = jsonObject.getJSONObject("rates");

            // Verileri ekrana yazdırıyoruz
            chfText.setText("CHF: " + rates.getString("CHF"));
            usdText.setText("USD: " + rates.getString("USD"));
            jpyText.setText("JPY: " + rates.getString("JPY"));
            tryText.setText("TRY: " + rates.getString("TRY"));
            cadText.setText("CAD: " + rates.getString("CAD"));

        } catch (Exception e) {
            Log.e("DownloadData", "JSON ayrıştırma hatası: " + e.getMessage());
            Toast.makeText(this, "Veri işlenirken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}