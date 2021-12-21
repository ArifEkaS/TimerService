package com.example.timerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class timerserviceactivity extends AppCompatActivity {

    private TextView mTextTimer;
    private Button mButtonStart, mButtonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerserviceactivity);
        mTextTimer = findViewById(R.id.tv_timer);
        mButtonStart = findViewById(R.id.bt_start);
        mButtonStop = findViewById(R.id.bt_stop);

        setupView();
    }

    private void setupView() {
        registerReceiver(broadcastReceiver, new IntentFilter(TimerService.BROADCAST_ACTION));

        mButtonStart.setOnClickListener(view -> {
            Intent intent = new Intent(timerserviceactivity.this, TimerService.class);
            intent.setAction(TimerService.ACTION_START_FOREGROUND_SERVICE);
            startService(intent);
        });

        mButtonStop.setOnClickListener(v -> {
            mTextTimer.setText("0:00");
            Intent intent = new Intent(timerserviceactivity.this, TimerService.class);
            intent.setAction(TimerService.ACTION_STOP_FOREGROUND_SERVICE);
            stopService(intent);
        });
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
        int mins = intent.getIntExtra("mins", 0);
        int secs = intent.getIntExtra("secs", 0);
        mTextTimer.setText("" + mins + ":" + String.format("%02d", secs));
    }
}