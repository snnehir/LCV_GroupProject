package com.example.lcv_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class CongratulationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_congratualations);

        Context ctx = this;
        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.wedding_straight3));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(ctx, MyInvitations.class));
            }
        });
    }
}