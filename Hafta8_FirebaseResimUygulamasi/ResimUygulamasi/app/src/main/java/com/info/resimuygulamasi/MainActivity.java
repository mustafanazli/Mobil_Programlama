package com.info.resimuygulamasi;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WallpaperAdapter.WallpaperClickListener {

    private RecyclerView recyclerView;
    private WallpaperAdapter adapter;
    private List<String> wallpaperUrls;

    // Picasso'nun zayıf referans (weak reference) problemini çözmek için Target'ı burada tutuyoruz.
    private Target picassoTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        wallpaperUrls = new ArrayList<>();
        adapter = new WallpaperAdapter(this, wallpaperUrls, this);
        recyclerView.setAdapter(adapter);

        loadWallpapers();
    }

    private void loadWallpapers() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("wallpapers");

        listRef.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    wallpaperUrls.add(uri.toString());
                    adapter.notifyDataSetChanged();
                });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity.this, "Resimler yüklenemedi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onWallpaperClick(String url) {
        picassoTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "Duvar kağıdı başarıyla değiştirildi!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Hata oluştu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Resim indirilemedi!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeholderDrawable) {
                Toast.makeText(MainActivity.this, "Duvar kağıdı ayarlanıyor...", Toast.LENGTH_SHORT).show();
            }
        };

        Picasso.get().load(url).into(picassoTarget);
    }
}