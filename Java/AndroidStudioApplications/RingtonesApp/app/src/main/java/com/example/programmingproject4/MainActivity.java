package com.example.programmingproject4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView bpimg, dimg, jimg, uimg;
    Button bpbutton, dbutton, jbutton, ubutton;
    MediaPlayer mpPipes, mpDrums, mpJig, mpUkelele;
    int playing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bpbutton = (Button)findViewById(R.id.bagpipesbtn);
        dbutton = (Button)findViewById(R.id.drumsbtn);
        jbutton = (Button)findViewById(R.id.jigbtn);
        ubutton = (Button)findViewById(R.id.ukelelebtn);
        bpimg = (ImageView)findViewById(R.id.bagpipesimg);
        dimg = (ImageView)findViewById(R.id.drumsimg);
        jimg = (ImageView)findViewById(R.id.violinimg);
        uimg = (ImageView)findViewById(R.id.ukeleleimg);



        bpbutton.setOnClickListener(bPipes);
        dbutton.setOnClickListener(bDrums);
        jbutton.setOnClickListener(bJig);
        ubutton.setOnClickListener(bUkelele);

        mpPipes = new MediaPlayer();
        mpPipes = MediaPlayer.create(this, R.raw.bagpipes);
        mpDrums = new MediaPlayer();
        mpDrums = MediaPlayer.create(this, R.raw.drums);
        mpJig = new MediaPlayer();
        mpJig = MediaPlayer.create(this, R.raw.jig);
        mpUkelele = new MediaPlayer();
        mpUkelele = MediaPlayer.create(this, R.raw.ukulele);

        playing = 0;
    }
    Button.OnClickListener bPipes = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
        switch (playing)
        {
            case 0:
                mpPipes.start();
                playing = 1;
                bpbutton.setText("Pause BagPipes");
                dbutton.setVisibility(View.INVISIBLE);
                jbutton.setVisibility(View.INVISIBLE);
                ubutton.setVisibility(View.INVISIBLE);
                dimg.setVisibility(View.INVISIBLE);
                jimg.setVisibility(View.INVISIBLE);
                uimg.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mpPipes.stop();
                playing = 0;
                bpbutton.setText("Play BagPipes");
                dbutton.setVisibility(View.VISIBLE);
                jbutton.setVisibility(View.VISIBLE);
                ubutton.setVisibility(View.VISIBLE);
                dimg.setVisibility(View.VISIBLE);
                jimg.setVisibility(View.VISIBLE);
                uimg.setVisibility(View.VISIBLE);
                break;
        }
        }
    };
    Button.OnClickListener bDrums = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (playing)
            {
                case 0:
                    mpDrums.start();
                    playing = 1;
                    dbutton.setText("Pause Drums");
                    bpbutton.setVisibility(View.INVISIBLE);
                    jbutton.setVisibility(View.INVISIBLE);
                    ubutton.setVisibility(View.INVISIBLE);
                    bpimg.setVisibility(View.INVISIBLE);
                    jimg.setVisibility(View.INVISIBLE);
                    uimg.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpDrums.stop();
                    playing = 0;
                    dbutton.setText("Play Drums");
                    bpbutton.setVisibility(View.VISIBLE);
                    jbutton.setVisibility(View.VISIBLE);
                    ubutton.setVisibility(View.VISIBLE);
                    bpimg.setVisibility(View.VISIBLE);
                    jimg.setVisibility(View.VISIBLE);
                    uimg.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    Button.OnClickListener bJig = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (playing)
            {
                case 0:
                    mpJig.start();
                    playing = 1;
                    jbutton.setText("Pause Jig");
                    bpbutton.setVisibility(View.INVISIBLE);
                    dbutton.setVisibility(View.INVISIBLE);
                    ubutton.setVisibility(View.INVISIBLE);
                    bpimg.setVisibility(View.INVISIBLE);
                    dimg.setVisibility(View.INVISIBLE);
                    uimg.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpJig.stop();
                    playing = 0;
                    jbutton.setText("Play Jig");
                    bpbutton.setVisibility(View.VISIBLE);
                    dbutton.setVisibility(View.VISIBLE);
                    ubutton.setVisibility(View.VISIBLE);
                    bpimg.setVisibility(View.VISIBLE);
                    dimg.setVisibility(View.VISIBLE);
                    uimg.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    Button.OnClickListener bUkelele = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (playing)
            {
                case 0:
                    mpUkelele.start();
                    playing = 1;
                    ubutton.setText("Pause Ukelele");
                    bpbutton.setVisibility(View.INVISIBLE);
                    dbutton.setVisibility(View.INVISIBLE);
                    jbutton.setVisibility(View.INVISIBLE);
                    bpimg.setVisibility(View.INVISIBLE);
                    dimg.setVisibility(View.INVISIBLE);
                    jimg.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpUkelele.stop();
                    playing = 0;
                    ubutton.setText("Play Ukelele");
                    bpbutton.setVisibility(View.VISIBLE);
                    dbutton.setVisibility(View.VISIBLE);
                    jbutton.setVisibility(View.VISIBLE);
                    bpimg.setVisibility(View.VISIBLE);
                    dimg.setVisibility(View.VISIBLE);
                    jimg.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}