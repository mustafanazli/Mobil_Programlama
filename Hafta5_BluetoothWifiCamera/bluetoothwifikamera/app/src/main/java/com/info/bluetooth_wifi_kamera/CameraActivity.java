package com.info.bluetooth_wifi_kamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    Button btnFotoCek, btnVideoCek;
    ImageView imageView;
    VideoView videoView;

    // Fotoğraf Çekme Sonucunu Yakalayıcı
    ActivityResultLauncher<Intent> fotoCekLauncher;
    // Video Çekme Sonucunu Yakalayıcı
    ActivityResultLauncher<Intent> videoCekLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Arayüz elemanlarını bağlama
        btnFotoCek = findViewById(R.id.btnFotoCek);
        btnVideoCek = findViewById(R.id.btnVideoCek);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);

        // Fotoğraf çekme işlemi bittiğinde ne olacağını tanımlıyoruz
        fotoCekLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            // Çekilen küçük resmi (thumbnail) alıyoruz
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");

                            // Videoyu gizle, resmi göster
                            videoView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                });

        // Video çekme işlemi bittiğinde ne olacağını tanımlıyoruz
        videoCekLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri videoUri = result.getData().getData();

                            // Resmi gizle, videoyu göster
                            imageView.setVisibility(View.GONE);
                            videoView.setVisibility(View.VISIBLE);

                            videoView.setVideoURI(videoUri);
                            // Videoyu kontrol edebilmek için (oynat/durdur) kontrolcü ekliyoruz
                            MediaController mediaController = new MediaController(CameraActivity.this);
                            mediaController.setAnchorView(videoView);
                            videoView.setMediaController(mediaController);
                            videoView.start(); // Otomatik oynatmaya başla
                        }
                    }
                });

        // Buton Tıklama Olayları
        btnFotoCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fotoCekLauncher.launch(fotoIntent);
            }
        });

        btnVideoCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoCekLauncher.launch(videoIntent);
            }
        });
    }
}