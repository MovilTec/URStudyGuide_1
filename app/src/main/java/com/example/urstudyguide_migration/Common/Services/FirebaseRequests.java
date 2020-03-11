package com.example.urstudyguide_migration.Common.Services;

import android.widget.Button;
import android.widget.TextView;

import com.example.urstudyguide_migration.AnswerText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRequests <V> {

    private static FirebaseRequests shared = null;
    private DatabaseReference userReference, quizzReference;

    public static FirebaseRequests getInstance() {
        if (shared == null)
            shared = new FirebaseRequests();

        return shared;
    }

    private FirebaseRequests() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users").child(FirebaseAuth.getInstance().getUid());
        quizzReference = database.getReference("Quizzes");
    }

    public void singleRequest(RequestType type, ValueEventListener listener) {
        switch (type) {
            case QUIZZ:
                quizzReference.addListenerForSingleValueEvent(listener);
            default:
                break;
        }
    }

    public void write(RequestType type, Object value, OnSuccessListener successListner, OnFailureListener failureListener) {
        switch (type) {
            case QUIZZ:
                quizzReference.push()
                        .setValue(value)
                        .addOnSuccessListener(successListner)
                        .addOnFailureListener(failureListener);
            default:
                break;
        }
    }

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
