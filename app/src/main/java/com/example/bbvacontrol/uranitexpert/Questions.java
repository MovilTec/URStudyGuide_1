package com.example.bbvacontrol.uranitexpert;

import android.widget.Button;
import android.widget.TextView;

import com.example.bbvacontrol.uranitexpert.Common.Services.FirebaseRequests;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Questions {


    FirebaseRequests requests = FirebaseRequests.getInstance();

    public void questionsRetrive(String ToppicReference, String question, final TextView textView, final Button button1, final Button button2, final Button button3){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference(ToppicReference);
        DatabaseReference RefQuestions = Ref.child("Questions");
        DatabaseReference RefQ = RefQuestions.child(question);

        DatabaseReference ClientSupportQuestion = RefQ.child("question");
        requests.databaseRequest(ClientSupportQuestion, textView);
        DatabaseReference DatabaseAnswer1 = RefQ.child("Answer1");
        requests.databaseButtonRequest(DatabaseAnswer1, button1);
        DatabaseReference DatabaseAnswer2 = RefQ.child("Answer2");
        requests.databaseButtonRequest(DatabaseAnswer2, button2);
        DatabaseReference DatabaseAnswer3 = RefQ.child("Answer3");
        requests.databaseButtonRequest(DatabaseAnswer3, button3);

    }

}
