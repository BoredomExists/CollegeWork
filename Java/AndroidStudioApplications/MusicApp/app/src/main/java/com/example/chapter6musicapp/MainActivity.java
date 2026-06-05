package com.example.chapter6musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    MediaPlayer thunder, roots;
    int playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.musicbtn);
        button2 = (Button) findViewById(R.id.musicbtn2);
        thunder = new MediaPlayer();
        thunder = MediaPlayer.create(this, R.raw.thunder);
        roots = new MediaPlayer();
        roots = MediaPlayer.create(this, R.raw.roots);
        playing = 0;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            switch (playing){
                case 0:
                    thunder.start();
                    playing = 1;
                    button1.setText("Pause");
                    button2.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    thunder.pause();
                    playing = 0;
                    button1.setText("Play");
                    button2.setVisibility(View.VISIBLE);
                    break;
            }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (playing){
                    case 0:
                        roots.start();
                        playing = 1;
                        button2.setText("Pause");
                        button1.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        roots.pause();
                        playing = 0;
                        button2.setText("Play");
                        button1.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}