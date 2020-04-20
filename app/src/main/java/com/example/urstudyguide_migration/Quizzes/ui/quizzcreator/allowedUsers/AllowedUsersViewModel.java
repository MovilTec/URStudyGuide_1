package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.Common.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class AllowedUsersViewModel extends ViewModel {

    AllowedUsersNavigator navigator;
    private List<UsersModelingClass> users = new ArrayList();
    private ArrayList<String> usersNames = new ArrayList();
    private Quizz mQuizz;

    public void setQuizz(Quizz quizz) {
        mQuizz = quizz;
    }

    public void getUsers() {
        FirebaseDatabase.getInstance().getReference("Users")
                .limitToFirst(50)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataUser: dataSnapshot.getChildren()) {
                    UsersModelingClass user = dataUser.getValue(UsersModelingClass.class);
                    if (!user.getId().equals(User.getInstance().getUserId())) {
                        users.add(user);
                        usersNames.add(user.getName());
                    }
                }
                navigator.onRetrieved(usersNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                navigator.onError(databaseError.getMessage());
            }
        });
    }

    public void selectedUser(String userName) {
        for (UsersModelingClass user : users) {
           if (user.getName().equals(userName)) {
                navigator.appendUserToRecyclerView(user);
                // Eliminating selected user
                usersNames.remove(userName);
                navigator.onRetrieved(usersNames);
            }
        }
    }

    public void createQuizzWith(HashMap<String, Object> allowedUsers) {
        mQuizz.setAllowed_users(allowedUsers);
        FirebaseDatabase.getInstance().getReference().child("Quizzes")
                .push()
                .setValue(mQuizz)
                .addOnSuccessListener(o -> {
                    navigator.onCreatedQuizz(mQuizz);
                })
                .addOnFailureListener(e -> {
                    navigator.onError(e.getLocalizedMessage());
                });


    }

}
