package com.example.urstudyguide_migration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ClientSupport extends AppCompatActivity {

    AnswerText answerText = new AnswerText();
    Questions questions = new Questions();
    ButtonAction buttonAction = new ButtonAction();
    final String Toppic = "ClientSupport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_support1);

        final Button startButton = findViewById(R.id.startClientSupportButton);
        final Button answer1 = findViewById(R.id.clientSupportAnswerButton1);
        final Button answer2 = findViewById(R.id.clientSupportAnswerButton2);
        final Button answer3 = findViewById(R.id.clientSupportAnswerButton3);
        final TextView textView = findViewById(R.id.textViewClientSupport);
        final TextView scoreTextView = findViewById(R.id.timerClientSupportView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Random random = new Random();
                final Integer numberQuestions = 3;
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

            }
        });
    }
}
