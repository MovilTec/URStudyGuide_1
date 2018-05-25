package com.example.bbvacontrol.uranitexpert;

import java.util.Random;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AnswerText {


    static int score = 0;

    String answerValue;

    public void storeAnswer(String answer, String id){
        answerValue = answer;
        if(answerValue != null){
            System.out.println("******* LA RESPUESTA ES: " + answerValue);

            if(answerValue.equals(id) == true){
                System.out.println("************* RESPUESTA CORRECTA *************************** " );
                score++;
            }else{
                System.out.println("************* RESPUESTA INCORRECTA *************************** " );
                System.out.println("************* SELECCION  "+ id + " Respues ***************************" + answerValue );
            }
        }
    }

    FirebaseRequests requests = new FirebaseRequests();

    public void retrivingAnswers(String ToppicReference, String question, String id) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference(ToppicReference);
        DatabaseReference answerRef = Ref.child("Answers");
        DatabaseReference answerOfQuestion = answerRef.child(question);
        System.out.println("The question that is going to retrieve is " + question);
        requests.answerRetrive(answerOfQuestion, id);

    }
}
