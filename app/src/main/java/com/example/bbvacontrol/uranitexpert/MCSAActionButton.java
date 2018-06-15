package com.example.bbvacontrol.uranitexpert;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MCSAActionButton {

    Questions questions = new Questions();
    AnswerText answerText = new AnswerText();
    Random random = new Random();
    AleatoryNumbers aleatoryNumbers = new AleatoryNumbers();
    //Timer timer = new Timer();

    boolean flag = false;
    int counter = 0;
    int number;

    public void buttonAction(final Button actionButton, final String id, final String toppingQuestion, final int numberQuestions, final TextView textView, final Button button1, final Button button2, final Button button3, final Button button4,  final Button mainButton, final TextView scoreView){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificadorCounter(button1, button2, button3, button4, textView, mainButton, scoreView, numberQuestions);
                Integer number = randomNumberCreator(numberQuestions);
                questions.questionsRetrive(toppingQuestion, "question" + number, textView, button1, button2, button3);
                answerText.retrivingAnswers(toppingQuestion, "question" + number, id);
            }
        });
    }

    private int randomNumberCreator(int arrayLenght){
        int randomNumber = random.nextInt(arrayLenght)+1;
        number = aleatoryNumbers.checkForRepeatedValues(randomNumber, arrayLenght);
        System.out.println("Numbers toma el valor: " + number + " dentro de Flag FALSE" );
        System.out.println(" counter value " + counter);
        return number;
    }

    private void verificadorCounter(Button button1, Button button2, Button button3, Button button4, TextView textView, Button actionButton, TextView scoreView, int lenght){
        if(counter == lenght){
            aleatoryNumbers.printAllAleatoryNumbers();
            reseatAleatoryNumbers();
            exit(button1, button2, button3, button4, textView, actionButton, scoreView);
        }else{
            counter++;
        }
    }

    private void exit(Button button1, Button button2, Button button3, Button button4, TextView textView, Button actionButton, TextView scoreView){
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setText("Try Again!");
        scoreView.setVisibility(View.VISIBLE);
        scoreView.setText("Your Score: " + getScore() );
        printScore();
        resetScore();
        counter = 0;
        flag = true;
    }

    private void printScore(){
        System.out.println("******** YOUR SCORE " + answerText.score + " ***********");
    }

    private int getScore(){
        int a = answerText.score;
        return a;
    }

    private void resetScore(){
        answerText.score = 0;
    }

    public void addElementToAleatoryNumbers(int value){
        aleatoryNumbers.addElements(value);
    }

    public void addToCounter(){
        counter++;
        System.out.println(" counter value: "  + counter );
    }

    public void reseatAleatoryNumbers(){
        aleatoryNumbers.restartAleatoryNumbers();
    }


}
