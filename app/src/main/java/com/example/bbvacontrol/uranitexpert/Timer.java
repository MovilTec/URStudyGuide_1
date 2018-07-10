package com.example.bbvacontrol.uranitexpert;

import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer extends Application{

    private static final long START_TIME_IN_MILLIS = 5000;
    private CountDownTimer downTimer;
    private boolean isTimmerRunning = true;
    private long mTimeLeft = START_TIME_IN_MILLIS;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public void countDownTimer(final TextView timerTextView,final Button answer1,final Button answer2,final Button answer3 ,final Button startButton, final TextView textView){

             downTimer = new CountDownTimer(mTimeLeft, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText( String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                startButton.setVisibility(View.VISIBLE);
                answer1.setVisibility(View.INVISIBLE);
                answer2.setVisibility(View.INVISIBLE);
                answer3.setVisibility(View.INVISIBLE);
                textView.setText("Try again!");
            }

        }.start();
    }

    public void resetTimer(){
        downTimer.cancel();
        isTimmerRunning = false;
        mTimeLeft = START_TIME_IN_MILLIS;
        downTimer.start();
    }

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public String getDataTimeStamp(long timeStamp){

        Date time = new Date(timeStamp);
        SimpleDateFormat pre = new SimpleDateFormat("EEE HH:mm aa");

        return pre.format(time);
    }

    public String getDateTimeStamp(long timeStamp){

        Date time = new Date(timeStamp);
        SimpleDateFormat pre = new SimpleDateFormat("EEE dd MM HH:mm aa YYYY");

        return pre.format(time);
    }

}
