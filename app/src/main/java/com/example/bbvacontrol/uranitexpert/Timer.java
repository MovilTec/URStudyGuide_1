package com.example.bbvacontrol.uranitexpert;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class Timer{

    private static final long START_TIME_IN_MILLIS = 5000;
    private CountDownTimer downTimer;
    private boolean isTimmerRunning = true;
    private long mTimeLeft = START_TIME_IN_MILLIS;

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
}
