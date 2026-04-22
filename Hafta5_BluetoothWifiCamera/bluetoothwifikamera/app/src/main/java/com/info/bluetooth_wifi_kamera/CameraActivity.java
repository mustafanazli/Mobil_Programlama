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

    ActivityResultLauncher<Intent> fotoCekLauncher;
    ActivityResultLauncher<Intent> videoCekLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnFotoCek = findViewById(R.id.btnFotoCek);
        btnVideoCek = findViewById(R.id.btnVideoCek);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);

        fotoCekLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");

                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                });

        videoCekLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri videoUri = result.getData().getData();

                            videoView.setVisibility(View.VISIBLE);

                            videoView.setVideoURI(videoUri);
                            MediaController mediaController = new MediaController(CameraActivity.this);
                            mediaController.setAnchorView(videoView);
                            videoView.setMediaController(mediaController);
                            videoView.start();
                        }
                    }
                });

        btnFotoCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fotoIntent.putExtra("android.intent.extras.CAMERA_FACING", 0);
                fotoCekLauncher.launch(fotoIntent);
            }
        });

        btnVideoCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra("android.intent.extras.CAMERA_FACING", 0);
                videoCekLauncher.launch(videoIntent);
            }
        });
    }
}