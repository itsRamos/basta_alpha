package com.example.basta_alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    TextView gameLetter;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    int timerDuration = 5;

    public void resetTimer() {
        timerTextView.setText("Ready");
//        timerSeekBar.setProgress(timerDuration);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void buttonClicked(View view){
        Log.i("Button Pressed", "Nice!");

        if (counterIsActive) {
            MediaPlayer mplayerwin = MediaPlayer.create(getApplicationContext(),R.raw.gamewin);
            mplayerwin.start();
            resetTimer();

        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
//            goButton.setBackgroundColor(Color.parseColor("#F44336"));
            goButton.setBackgroundColor(Color.RED);
            goButton.setText("BASTA!");

            gameLetter = findViewById(R.id.displayText);
            gameLetter.setText(getRandomLetter(1));

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished","Timer all done");

                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.gameover);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }


    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerText);
        goButton = findViewById(R.id.triggerButton);

        timerSeekBar.setMax(timerDuration);
        timerSeekBar.setProgress(timerDuration);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public static String getRandomLetter(int i) {
        final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder result = new StringBuilder();

        while (i > 0) {
            Random rand = new Random();
            result.append(character.charAt(rand.nextInt(character.length())));
            i--;
        }
        return result.toString();
    }


    public boolean checkRepeat(String genchar, String charString) {
        int i;
        boolean flag = false;

        for (i = 0; i < charString.length(); i++) {
            if (genchar.equals(charString.charAt(i))) {
                flag = true;
            }
            else
                flag = false;
        }
        return flag;
    }
}
