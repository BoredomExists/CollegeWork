package com.example.galegarejected;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {

    MediaPlayer bgmusic;
    ImageButton unmutedmusic, mutedmusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        bgmusic = new MediaPlayer();
        bgmusic = MediaPlayer.create(this, R.raw.firstactivitymusic);
        Button start = (Button) findViewById(R.id.FAStartButton);
        Button stop = (Button) findViewById(R.id.FAExitButton);
        unmutedmusic = (ImageButton) findViewById(R.id.unmutedbutton);
        mutedmusic = (ImageButton) findViewById(R.id.mutedbutton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, GameActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                bgmusic.stop();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                bgmusic.stop();
            }
        });
        unmutedmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bgmusic.pause();
                unmutedmusic.setVisibility(View.INVISIBLE);
                mutedmusic.setVisibility(View.VISIBLE);
            }
        });
        mutedmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bgmusic.start();
                mutedmusic.setVisibility(View.INVISIBLE);
                unmutedmusic.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        bgmusic.pause();
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        bgmusic.start();
        bgmusic.setLooping(true);
        mutedmusic.setVisibility(View.INVISIBLE);
    }
}