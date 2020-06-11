package com.example.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private long startTime;
    private long elapsedTime = 0l;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private TextView timerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        timerLabel = (TextView) findViewById(R.id.timerLabel);

        setButtonState(true, false, false);
    }

    public void setButtonState(boolean start, boolean stop, boolean reset){
        startButton.setEnabled(start);
        stopButton.setEnabled(stop);
        resetButton.setEnabled(reset);
    }

    public void startTimer(View view){
        startTime = SystemClock.elapsedRealtime();

        //Handler -> Runnable(処理)
        updateTimer = new Runnable() {
            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elapsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
                timerLabel.setText(sdf.format(t));
                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer, 10);
            }
        };
        handler.postDelayed(updateTimer, 10);

        setButtonState(false,true,false);
    }

    public void stopTimer(View view){
        elapsedTime += SystemClock.elapsedRealtime() - startTime;
        handler.removeCallbacks(updateTimer);
        setButtonState(true,false,true);
    }

    public void resetTimer(View view){
        elapsedTime = 0l;
        timerLabel.setText(R.string.timer_label);
        setButtonState(true,false,false);

    }
}
