package com.example.timer;

import android.media.MediaPlayer;
import android.widget.Chronometer;
import android.widget.TextView;

public class ChronoParams implements Chronometer.OnChronometerTickListener {
    private int totalTime;
    private int firstBip;
    private int singleBipDelay;
    private int doubleBipDelay;
    private MediaPlayer mediaPlayer;

    public ChronoParams(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    int timer = -2;
    int counter = 0;

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        timer++;
        if (totalTime != 0){
            if (timer == totalTime)
                chronometer.stop();}

        if(timer >= firstBip){

            if(singleBipDelay != 0){
                int reste = (timer-firstBip) % singleBipDelay;
                if ( reste == 0 ){
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.seekTo(0);
                    else
                        mediaPlayer.start();
                    counter++;
                } }

            if(doubleBipDelay != 0){
                int reste = (timer-firstBip) % doubleBipDelay;
                if ( reste == 1 ){
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.seekTo(0);
                    else
                        mediaPlayer.start();
                    counter++;
                } }
        }
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getFirstBip() {
        return firstBip;
    }

    public void setFirstBip(int firstBip) {
        this.firstBip = firstBip;
    }

    public int getSingleBipDelay() {
        return singleBipDelay;
    }

    public void setSingleBipDelay(int singleBipDelay) {
        this.singleBipDelay = singleBipDelay;
    }

    public int getDoubleBipDelay() {
        return doubleBipDelay;
    }

    public void setDoubleBipDelay(int doubleBipDelay) {
        this.doubleBipDelay = doubleBipDelay;
    }
}
