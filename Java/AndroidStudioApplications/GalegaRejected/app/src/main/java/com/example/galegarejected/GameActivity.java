package com.example.galegarejected;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    AnimationDrawable enemy1anim, enemy2anim, enemy3anim, dmgboostanim, shieldanim, healthgainanim, gamebganim;
    ImageView dmgboost, shieldgain, healthgain;
    ImageView player, bullet, ebullet;
    ImageView heart1, heart2, heart3;
    Button button;

    ImageView cBoxY1;
    ImageView cBoxY2;
    ImageView cBoxX1;
    ImageView cBoxX2;

    ImageView sEnemyHB;

    ArrayList<ImageView> sEnemies;
    ArrayList<ImageView> mEnemies;
    ImageView lEnemy;

    Handler heartHandler;

    int maxHealth = 100;
    int originX, originY;
    float xDelta = 0, yDelta = 0;
    float moveX, moveY, distanceX, distanceY;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        player = findViewById(R.id.player);
        bullet = findViewById(R.id.bullet);
        ebullet = findViewById(R.id.ebullet);

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        cBoxY1 = findViewById(R.id.colboxY1);
        cBoxY2 = findViewById(R.id.colboxY2);
        cBoxX1 = findViewById(R.id.colBoxX1);
        cBoxX2 = findViewById(R.id.colBoxX2);

        sEnemyHB = findViewById(R.id.sEnemyCol);

        button = findViewById(R.id.button);
        heartHandler = new Handler();

        PlayerMovement();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Background animation & Generates all other Animations
        ImageView gameBackground = (ImageView) findViewById(R.id.gamebg);
        gameBackground.setBackgroundResource(R.drawable.gamebganimation);
        gamebganim = (AnimationDrawable) gameBackground.getBackground();
        gamebganim.start();
        EnemyAnimations();
        PowerUpAnimations();
        TestHearts(heart1);

    }


    //Bullet Items (Player & Enemy)
    //Pop Ups
    // Health Bar needs Checked or reworked
    // Game Over Screen & Winner Screen

    public void TestHearts(ImageView h)
    {
        //Fix needed
    }










    public void PlayerMovement() {
        player.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                originX = (int) player.getX();
                originY = (int) player.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = event.getX();
                        yDelta = event.getY();

                        bullet.setY(player.getY() - 50);
                        bullet.setX(player.getX() + 40);
                        bullet.setBackgroundResource(R.drawable.bullet);

                        break;

                    case MotionEvent.ACTION_UP:
                        bullet.setBackgroundResource(0);
                        break;


                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        moveY = event.getY();

                        distanceX = moveX - xDelta;
                        distanceY = moveY - yDelta;

                        player.setX(player.getX() + distanceX);
                        player.setY(player.getY() + distanceY);

                        if (CollisionY(player, cBoxY1, cBoxY2)) {
                            player.setY(originY);
                        }
                        if (CollisionX(player, cBoxX1, cBoxX2)) {
                            player.setX(originX);
                        }

                        BulletAnimation(bullet);

                        if(SmallEnemyCollision(sEnemyHB, bullet))
                        {
                            sEnemies.get(0).setBackgroundResource(0);
                        }


                        if (bullet.getY() <= -100.0f) {
                            bullet.setY(player.getY() - 50);
                            bullet.setX(player.getX() + 40);
                        }

                        break;
                }
                return true;
            }
        });
    }

    public boolean HealCollision(ImageView p, ImageView h) {
        Rect rectPlayer = new Rect();
        Rect rectHealth = new Rect();
        p.getHitRect(rectPlayer);
        h.getHitRect(rectHealth);
        return rectPlayer.intersect(rectHealth);
    }

    public boolean CollisionY(ImageView p, ImageView c1, ImageView c2) {
        Rect rectPlayer = new Rect();
        Rect rectY1 = new Rect();
        Rect rectY2 = new Rect();
        p.getHitRect(rectPlayer);
        c1.getHitRect(rectY1);
        c2.getHitRect(rectY2);
        return rectPlayer.intersect(rectY1) || rectPlayer.intersect(rectY2);
    }

    public boolean CollisionX(ImageView p, ImageView c1, ImageView c2) {
        Rect rectPlayer = new Rect();
        Rect rectX1 = new Rect();
        Rect rectX2 = new Rect();
        p.getHitRect(rectPlayer);
        c1.getHitRect(rectX1);
        c2.getHitRect(rectX2);

        return rectPlayer.intersect(rectX1) || rectPlayer.intersect(rectX2);
    }

    public boolean SmallEnemyCollision(ImageView e, ImageView b)
    {
        Rect rectBullet = new Rect();
        Rect smallEnemy = new Rect();
        e.getHitRect(smallEnemy);
        b.getHitRect(rectBullet);

        return rectBullet.intersect(smallEnemy);
    }



    //Bullet Animation is working (Touch ups needed)
    public void BulletAnimation(ImageView v) {
        float currentBulletY = v.getY();
        v.setY(currentBulletY - 50);
    }


    //Set EnemyAnimations like Bullet
    public void EnemyAnimations() {
        // Enemy 1 animation
        TranslateAnimation sEnemyRight = new TranslateAnimation(0.0f, 500.0f, 0.0f, 0.0f);
        sEnemyRight.setDuration(2500);
        sEnemies = new ArrayList<>();
        sEnemies.add((ImageView) findViewById(R.id.smallenemy1));
        sEnemies.add((ImageView) findViewById(R.id.smallenemy2));
        sEnemies.add((ImageView) findViewById(R.id.smallenemy3));
        sEnemies.add((ImageView) findViewById(R.id.smallenemy4));
        for (int i = 0; i < sEnemies.size(); i++) {
            sEnemies.get(i).setBackgroundResource(R.drawable.enemy1animation);
            enemy1anim = (AnimationDrawable) sEnemies.get(i).getBackground();
            enemy1anim.start();
            sEnemies.get(i).startAnimation(sEnemyRight);
        }


        // Enemy 2 animation
        TranslateAnimation mEnemyRight = new TranslateAnimation(0.0f, 200.0f, 0.0f, 0.0f);
        TranslateAnimation mEnemyLeft = new TranslateAnimation(0.0f, -200.0f, 0.0f, 0.0f);
        mEnemyRight.setDuration(2500);
        mEnemyLeft.setDuration(2500);
        mEnemies = new ArrayList<>();
        mEnemies.add((ImageView) findViewById(R.id.mediumenemy1));
        mEnemies.add((ImageView) findViewById(R.id.mediumenemy2));
        for (int i = 0; i < mEnemies.size(); i++) {
            mEnemies.get(i).setBackgroundResource(R.drawable.enemy2animation);
            enemy2anim = (AnimationDrawable) mEnemies.get(i).getBackground();
            enemy2anim.start();
        }
        mEnemies.get(0).startAnimation(mEnemyRight);
        mEnemies.get(1).startAnimation(mEnemyLeft);

        mEnemyRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mEnemies.get(0).setX(220);
                mEnemies.get(0).startAnimation(mEnemyLeft);

                mEnemies.get(1).setX(500);
                mEnemies.get(1).startAnimation(mEnemyRight);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mEnemyLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mEnemies.get(0).setX(20);
                mEnemies.get(0).startAnimation(mEnemyRight);

                mEnemies.get(1).setX(700);
                mEnemies.get(1).startAnimation(mEnemyLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Enemy 3 animation
        lEnemy = (ImageView) findViewById(R.id.largeenemy);
        lEnemy.setBackgroundResource(R.drawable.enemy3animation);
        enemy3anim = (AnimationDrawable) lEnemy.getBackground();
        enemy3anim.start();
        LargeEnemyAnimation(lEnemy);
    }

    public void PowerUpAnimations() {
        // DmgBoost Animation
        dmgboost = (ImageView) findViewById(R.id.dmgboost);
        dmgboost.setBackgroundResource(R.drawable.dmgboostanimation);
        dmgboostanim = (AnimationDrawable) dmgboost.getBackground();
        dmgboostanim.start();

        // Shield Animation
        shieldgain = (ImageView) findViewById(R.id.shield);
        shieldgain.setBackgroundResource(R.drawable.shieldanimation);
        shieldanim = (AnimationDrawable) shieldgain.getBackground();
        shieldanim.start();

        // HealthGain Animation
        healthgain = (ImageView) findViewById(R.id.healthgain);
        healthgain.setBackgroundResource(R.drawable.healthgainanimation);
        healthgainanim = (AnimationDrawable) healthgain.getBackground();
        healthgainanim.start();
    }





    public void LargeEnemyAnimation(ImageView v)
    {
        TranslateAnimation lEnemyAnim1 = new TranslateAnimation(0.0f, 100.0f, 0.0f, 150.0f);
        TranslateAnimation lEnemyAnim1_5 = new TranslateAnimation(0.0f, 0.0f, 0.0f, -175.0f);
        TranslateAnimation lEnemyAnim2 = new TranslateAnimation(0.0f, 0.0f, 0.0f, -125.0f);
        TranslateAnimation lEnemyAnim3 = new TranslateAnimation(0.0f, -300.0f, 0.0f, 125.0f);
        TranslateAnimation lEnemyAnim4 = new TranslateAnimation(0.0f, 325.0f, 0.0f, 200.0f);
        lEnemyAnim1.setDuration(2500);
        lEnemyAnim1_5.setDuration(2500);
        lEnemyAnim2.setDuration(2500);
        lEnemyAnim3.setDuration(2500);
        lEnemyAnim4.setDuration(2500);

        v.startAnimation(lEnemyAnim1);
        lEnemyAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setX(450.0f);
                v.setY(175.0f);
                v.startAnimation(lEnemyAnim2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lEnemyAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            v.setY(75.0f);
            v.startAnimation(lEnemyAnim3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lEnemyAnim3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setY(175.0f);
                v.setX(150.0f);
                v.startAnimation(lEnemyAnim1_5);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lEnemyAnim1_5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            v.setY(0.0f);
            v.startAnimation(lEnemyAnim4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lEnemyAnim4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setX(475.0f);
                v.setY(200.0f);
                v.startAnimation(lEnemyAnim2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}