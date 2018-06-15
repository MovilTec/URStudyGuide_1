package com.example.bbvacontrol.uranitexpert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Users {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private  FirebaseUser mCurrentUser;

    private String UserName;
    private String UsersStatus;

//    public void register_user(String email, String password){
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }

    public void registerNewUser(String user_name){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = current_user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        HashMap<String, String> userMap = new HashMap();
        userMap.put("name", user_name);
        userMap.put("status", "Hi there!, I'm using URStudyGuide App.");
        userMap.put("image", "default");
        userMap.put("thumb_image", "default");

        mDatabase.setValue(userMap);

    }

    public void setUserInfo(){

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String user_nickName = dataSnapshot.child("name").getValue().toString();
                String user_image = dataSnapshot.child("image").getValue().toString();
                String user_status = dataSnapshot.child("status").getValue().toString();
                String user_thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                UserName = user_nickName;
                UsersStatus = user_status;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String getUserName(){
        return UserName;
    }

    public String getUsersStatus(){
        return UsersStatus;
    }

    public void setUserNewStatus(String newStatus){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);
        mDatabase.child("status").setValue(newStatus);

    }

    public void getCurrentUserInfo(final TextView user_nickName_TextView, final TextView user_status_TextView){

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String user_nickName = dataSnapshot.child("name").getValue().toString();
                String user_image = dataSnapshot.child("image").getValue().toString();
                String user_status = dataSnapshot.child("status").getValue().toString();
                String user_thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                user_nickName_TextView.setText(user_nickName);
                user_status_TextView.setText(user_status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
