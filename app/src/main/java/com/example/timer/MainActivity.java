package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ChronoParams chronoParams;
    private Chronometer wTimeDisplay;
    private EditText wTotalTime;
    private EditText wFirstBip;
    private EditText wSingleDelay;
    private EditText wDoubleDelay;
    private Button wStart;
    private Button wReset;
    private MediaPlayer mediaPlayer;
    private TextView mLogger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wTimeDisplay = findViewById(R.id.time_display);
        wStart = findViewById(R.id.bt_start);
        wReset = findViewById(R.id.bt_reset);
        wTotalTime = findViewById(R.id.et_total_time);
        wFirstBip = findViewById(R.id.et_first_bip);
        wSingleDelay = findViewById(R.id.et_single_delay);
        wDoubleDelay = findViewById(R.id.et_double_bip);

        mediaPlayer = MediaPlayer.create(this,R.raw.bip);

        wTimeDisplay.setBase(SystemClock.elapsedRealtime());

       chronoParams = new ChronoParams(mediaPlayer);
       wTimeDisplay.setOnChronometerTickListener(chronoParams);

        wStart.setOnClickListener(new View.OnClickListener() {
            int totalTime;
            int firstBip;
            int singleDelay;
            int doubleDelay;
            @Override
            public void onClick(View v) {
                totalTime = getInput(wTotalTime, "the total time");
                firstBip = getInput(wFirstBip,"the first bip");
                singleDelay = getInput(wSingleDelay,"single bip delay");
                doubleDelay = getInput(wDoubleDelay,"double bip delay");
                chronoParams.timer = -2;
                chronoParams.counter = 0;
                chronoParams.setTotalTime(totalTime);
                chronoParams.setFirstBip(firstBip);
                chronoParams.setSingleBipDelay(singleDelay);
                chronoParams.setDoubleBipDelay(doubleDelay);
                wTimeDisplay.setBase(SystemClock.elapsedRealtime());
                wTimeDisplay.start();
            }

            int getInput(EditText view, String type){
                String input;
                input = view.getText().toString();
                if (!input.equals("")){
                    try {
                        return Integer.parseInt(input);
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(), "Type " + type + " in seconds!", Toast.LENGTH_LONG).show();
                        return 0;
                    }
                }
                else
                    return 0;
            }
        });

        wReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wTimeDisplay.stop();
                wTimeDisplay.setBase(SystemClock.elapsedRealtime());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(this,R.raw.bip);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_player_activity:
                Intent intent = new Intent(MainActivity.this, AudioPlayerActivity.class);
                startActivity(intent);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playAudio(View view) {
        mediaPlayer.start();
    }
}
