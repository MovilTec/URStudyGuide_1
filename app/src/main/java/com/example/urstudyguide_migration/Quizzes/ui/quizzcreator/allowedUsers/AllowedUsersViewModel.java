package com.example.urstudyguide_migration.Quizzes.ui.quizzcreator.allowedUsers;

import android.os.Build;

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
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

public class AllowedUsersViewModel extends ViewModel {

    AllowedUsersNavigator navigator;
    private List<UsersModelingClass> users = new ArrayList();
    private ArrayList<String> usersNames = new ArrayList();
    private Quizz mQuizz;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setQuizz(Quizz quizz) {
        mQuizz = quizz;
    }

    private void setAlreadyMembers() {
        //This case is supossed when comes from detail view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if (mQuizz.getAllowed_users().size() > 0) {
                // Changint the save quizz button text
                navigator.changeSaveQuizzButton();

                List<Object> objects = new ArrayList<Object>(mQuizz.getAllowed_users().values());
                ArrayList<String> usersIds;
                usersIds = (ArrayList<String>) objects.stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
                for (String userId : usersIds) {
                    for (UsersModelingClass user : users) {
                        if (user.getId().equals(userId)) {
                            navigator.appendUserToRecyclerView(user);
                        }
                    }
                }
            }
        }
    }

    public void getUsers() {
        String path = "Users_Relationships/" + User.getInstance().getUserId();
        FirebaseDatabase.getInstance().getReference(path)
                .limitToFirst(50)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child_dataSnapshot: dataSnapshot.getChildren()) {
                    String userId = child_dataSnapshot.getKey();
                    FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());

                    String userPath = "Users/" + userId;
                    CompletableFuture<DataSnapshot> completableTask = firebaseManager.read(userPath);

                    completableTask.thenAccept(dataSnapshot1 -> {
                        UsersModelingClass user = dataSnapshot1.getValue(UsersModelingClass.class);
                        //TODO:- if comes from edit remove alredy in users
                        if (!user.getId().equals(User.getInstance().getUserId())) {
                            users.add(user);
                            usersNames.add(user.getName());
                        }
                    });
                }
                navigator.onRetrieved(usersNames);
                //TODO:- This migth not be the best place to call this function
                setAlreadyMembers();
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
