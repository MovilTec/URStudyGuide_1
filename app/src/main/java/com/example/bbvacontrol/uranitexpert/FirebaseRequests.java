package com.example.bbvacontrol.uranitexpert;

import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRequests {

    public void databaseRequest(DatabaseReference databaseReference, final TextView textView){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textView.setText((CharSequence) databaseError.toException());
            }
        });

    }

    public void databaseButtonRequest(DatabaseReference databaseReference, final Button button){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                button.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                button.setText((CharSequence) databaseError.toException());
            }
        });

    }

    public void answerRetrive(DatabaseReference databaseReference, final String id){
        
            final Integer score = 0;

        databaseReference.addValueEventListener(new ValueEventListener() {
            String Value;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                AnswerText answerText = new AnswerText();
                answerText.storeAnswer(value, id);
                System.out.println("The correct answer is : " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });
    }
}
