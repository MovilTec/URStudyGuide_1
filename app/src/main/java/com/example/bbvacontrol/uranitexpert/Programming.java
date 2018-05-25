package com.example.bbvacontrol.uranitexpert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class Programming extends AppCompatActivity {

    AnswerText answerText = new AnswerText();
    ButtonAction buttonAction = new ButtonAction();
    Questions questions = new Questions();
    final String Toppic = "Programming";

    Integer score = 0;

    boolean Answer1 = false;
    boolean Answer2 = false;
    boolean Answer3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming);

        final Button startButton = findViewById(R.id.startProgrammingButton);
        final Button answer1 = findViewById(R.id.programmingAnswerButton1);
        final Button answer2 = findViewById(R.id.programmingAnswerButton2);
        final Button answer3 = findViewById(R.id.programmingAnswerButton3);
        final TextView textView = findViewById(R.id.programmingTextView);
        final TextView scoreTextView = findViewById(R.id.timerProgrammingView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Random random = new Random();
                final Integer numberQuestions = 6;
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

                questions.questionsRetrive(Toppic, "question" + randomNumber,textView, answer1, answer2, answer3);
                //answerText.retrivingAnswers("Programming", "question" + randomNumber, "Answer1");

                buttonAction.buttonAction(answer1, "Answer1", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
                buttonAction.buttonAction(answer2, "Answer2", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
                buttonAction.buttonAction(answer3, "Answer3", Toppic, numberQuestions,textView, answer1, answer2, answer3, startButton, scoreTextView);
            }
        });

    }

}
