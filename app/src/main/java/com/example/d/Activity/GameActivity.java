package com.example.d.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d.databinding.ActivityGameBinding;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private int score;
    private ImageView[] imageArray;
    private Handler handler;
    private Runnable runnable;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);


        ViewCompat.setOnApplyWindowInsetsListener(binding.score, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ImageView dizisi tanımlama
        imageArray = new ImageView[] {
                binding.imageView, binding.imageView1, binding.imageView2,
                binding.imageView3, binding.imageView4, binding.imageView5,
                binding.imageView6, binding.imageView7, binding.imageView8,
                binding.imageView9, binding.imageView10, binding.imageView11,
                binding.imageView12, binding.imageView13, binding.imageView14,
                binding.imageView15
        };


        score = 0;
        binding.scoreTxt.setText("Skor: " + score);
        startGameTimer();
        startImageHandler();
    }

    private void startGameTimer() {

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.time.setText("Süre: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                saveScore(score);
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Intent scoreIntent = new Intent(GameActivity.this, ScoreActivity.class);
                startActivity(scoreIntent);
                finish();
            }
        }.start();
    }

    private void startImageHandler() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }


                Random random = new Random();
                int i = random.nextInt(imageArray.length);
                imageArray[i].setVisibility(View.VISIBLE);


                handler.postDelayed(this, 800);
            }
        };


        handler.post(runnable);
    }

    public void increaseScore(View view) {

        score++;
        binding.scoreTxt.setText("Skor: " + score);
    }

    private void saveScore(int score) {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("score", score);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
