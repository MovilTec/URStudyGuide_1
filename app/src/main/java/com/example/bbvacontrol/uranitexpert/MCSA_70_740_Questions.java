package com.example.bbvacontrol.uranitexpert;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MCSA_70_740_Questions extends AppCompatActivity{

    private Toolbar mToolbar;

    String Toppic = "MCSA-70-740_Unit3";
    MCSAActionButton buttonAction = new MCSAActionButton();
    Questions questions = new Questions();
    String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcsa_70_740__questions);

        mToolbar  = findViewById(R.id.MCSA_70_740_questions_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button startButton = findViewById(R.id.MCSA_startButton);
        final Button answerButton1 = findViewById(R.id.MCSA_answerButton1);
        final Button answerButton2 = findViewById(R.id.MCSA_answerButton2);
        final Button answerButton3 = findViewById(R.id.MCSA_answerButton3);
        final Button answerButton4 = findViewById(R.id.MCSA_answerButton4);
        final TextView questionView = findViewById(R.id.MCSA_questionTextView);
        final TextView scoreView = findViewById(R.id.MCSA_score_textView);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Random random = new Random();
                final Integer numberQuestions = 4;
                final Integer randomNumber = random.nextInt(numberQuestions)+1;

                buttonAction.reseatAleatoryNumbers();
                buttonAction.addElementToAleatoryNumbers(randomNumber);
                buttonAction.addToCounter();
                System.out.println("Se envia valor "  + randomNumber + " al arreglo de Numeros aleatoreos por 1ra vez");
                //Configurando visibilidad de los Elementos
                viewSettings();

                questions.questionsRetrive(Toppic, "question" + randomNumber,questionView, answerButton1, answerButton2, answerButton3);

                buttonAction.buttonAction(answerButton1, "Answer1", Toppic, numberQuestions,questionView, answerButton1, answerButton2, answerButton3, answerButton4, startButton, scoreView);
                buttonAction.buttonAction(answerButton2, "Answer2", Toppic, numberQuestions,questionView, answerButton1, answerButton2, answerButton3, answerButton4, startButton, scoreView);
                buttonAction.buttonAction(answerButton3, "Answer3", Toppic, numberQuestions,questionView, answerButton1, answerButton2, answerButton3, answerButton4, startButton, scoreView);

            }
        });
    }

    public void setToppic(String toppic){
        Toppic = toppic;
    }

    public void setTitle(String toppic){
        Title = toppic;
        System.out.println(Title);
    }

    public void updateTextView(String title){
//        TextView questionView = (TextView) ((Activity)context).findViewById(R.id.MCSA_questionTextView);
//        questionView.setText(title);
}

    private void viewSettings(){
        final Button startButton = findViewById(R.id.MCSA_startButton);
        final Button answerButton1 = findViewById(R.id.MCSA_answerButton1);
        final Button answerButton2 = findViewById(R.id.MCSA_answerButton2);
        final Button answerButton3 = findViewById(R.id.MCSA_answerButton3);
        final Button answerButton4 = findViewById(R.id.MCSA_answerButton4);
        final TextView questionView = findViewById(R.id.MCSA_questionTextView);
        final TextView scoreView = findViewById(R.id.MCSA_score_textView);

        startButton.setVisibility(View.INVISIBLE);
        answerButton1.setVisibility(View.VISIBLE);
        answerButton2.setVisibility(View.VISIBLE);
        answerButton3.setVisibility(View.VISIBLE);
        answerButton4.setVisibility(View.VISIBLE);
        //textView.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.INVISIBLE);
    }
}

