package com.example.bbvacontrol.uranitexpert;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

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


    public void getUserImage(String user_ID, final CircleImageView user_image){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userImage = dataSnapshot.child("thumb_image").getValue().toString();
                if(!userImage.equals("default")) {
                    Picasso.get().load(userImage).into(user_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserName(String user_ID, final TextView userNameTextView){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserName = dataSnapshot.child("name").getValue().toString();
                userNameTextView.setText(UserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setBarUserTitle(String user_ID, final Toolbar mToolbar){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserName = dataSnapshot.child("name").getValue().toString();
                mToolbar.setTitle(UserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getUsersStatus(String user_ID, final TextView userStatusTextView){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserStatus = dataSnapshot.child("status").getValue().toString();
                userStatusTextView.setText(UserStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getUserID(){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_userID = mCurrentUser.getUid();
        return current_userID;
    }

    public void setUserNewStatus(String newStatus){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);
        mDatabase.child("status").setValue(newStatus);

    }

    public void setUserNewImage(String newImage, final ProgressDialog mProgressDialog, final Context context, final StorageReference thumbNailImage_reference){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID());
        mDatabase.child("image").setValue(newImage).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //mProgressDialog.dismiss();
                    setNewUser_thumbnailImageURL(mProgressDialog, context, thumbNailImage_reference);
                    Toast.makeText(context, "Sucess.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setNewUser_thumbnailImageURL(final ProgressDialog mProgressDialog, final Context context, StorageReference thumb_storage_reference){

        thumb_storage_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID());
                String downladURL = uri.toString();
                mDatabase.child("thumb_image").setValue(downladURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgressDialog.dismiss();
                            Toast.makeText(context, "Sucess.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void getCurrentUserInfo(final TextView user_nickName_TextView, final TextView user_status_TextView, final CircleImageView user_avatar_image){

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

                if(!user_image.equals("default")) {
                    Picasso.get().load(user_image).into(user_avatar_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
