package com.example.bbvacontrol.uranitexpert;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class NetworkActivity extends AppCompatActivity {

    Questions questions = new Questions();
    ButtonAction buttonAction = new ButtonAction();
    final String Toppic = "Network";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        final Button answer1 = (Button) findViewById(R.id.networkAnswerButton1);
        final Button answer2 = (Button) findViewById(R.id.networkAnswerButton2);
        final Button answer3 = (Button) findViewById(R.id.networkAnswerButton3);
        final Button startButton = (Button) findViewById(R.id.startNetworkButton);
        final TextView textView = (TextView) findViewById(R.id.textViewNetwork);
        final TextView scoreTextView = (TextView) findViewById(R.id.timerNetworkView);

        startButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Random random = new Random();
                final Integer numberQuestions = 4;
                final Integer randomNumber = random.nextInt(numberQuestions)+1;

                buttonAction.reseatAleatoryNumbers();
                buttonAction.addElementToAleatoryNumbers(randomNumber);
                buttonAction.addToCounter();
                System.out.println("Se envia valor "  + randomNumber + " al arreglo de Numeros aleatoreos por 1ra vez");
                startButton.setVisibility(View.INVISIBLE);
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                scoreTextView.setVisibility(View.INVISIBLE);

                questions.questionsRetrive(Toppic,"question" + randomNumber, textView, answer1, answer2, answer3);

                buttonAction.buttonAction(answer1, "Answer1", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
                buttonAction.buttonAction(answer2, "Answer2", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
                buttonAction.buttonAction(answer3, "Answer3", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
                //timer.countDownTimer(timerTextView,answer1,answer2, answer3, playButton, textview);

            }
        });
    }
}
