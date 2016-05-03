package com.sibela.examples.smswarningreceiver;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WarningActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        ButterKnife.bind(this);

        setScreenBrightnessMax();
        setVolumeOnMax();
        turnOnScreen();
        startAlarm();
        vibrate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAlarm();
    }

    private void setScreenBrightnessMax() {
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
    }

    private void startAlarm() {
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.acdc_kicked_in_the_teeth);
        mediaPlayer.start();
    }

    private void setVolumeOnMax() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    private void vibrate() {
        vibrator = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000, 300, 1000, 400, 1000, 500, 2000, 2000};
        vibrator.vibrate(pattern, -1);
    }

    private void turnOnScreen() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    @OnClick(R.id.cancel_layout)
    public void finish() {
        super.finish();
    }

    public void cancelAlarm() {
        vibrator.cancel();
        vibrator = null;

        mediaPlayer.release();
        mediaPlayer = null;
    }
}
