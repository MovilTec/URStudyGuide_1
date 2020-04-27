package com.example.urstudyguide_migration.Quizzes.ui.allowedUsers;

import android.os.Build;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.Quizz;
import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.Quizzes.navigators.AllowedUsersNavigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

    public void getUsers() {
        FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
        String path = "Users_Relationships/" + User.getInstance().getUserId();
        CompletableFuture<DataSnapshot> usersPromise = firebaseManager.read(path);

        usersPromise.thenAccept(dataSnapshot ->  {
            List<String> users_paths = new ArrayList();
            dataSnapshot.getChildren().forEach(child -> {
                String userId = child.getKey();
                String userPath = "Users/" + userId;
                users_paths.add(userPath);
            });

            List<CompletableFuture<DataSnapshot>> usersPromises = users_paths.stream()
                    .map( _path ->  firebaseManager.read(_path) )
                    .collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    usersPromises.toArray(new CompletableFuture[usersPromises.size()])
            );

            CompletableFuture<List<DataSnapshot>> allPageContentsFuture = allFutures.thenApply(v -> {
                return usersPromises.stream()
                        .map(userPromise -> userPromise.join())
                        .collect(Collectors.toList());
            });

            allPageContentsFuture.thenAccept(listOfUsers -> {
                listOfUsers.forEach(userDataSnapshot -> {
                    UsersModelingClass user = userDataSnapshot.getValue(UsersModelingClass.class);
                    //TODO:- if comes from edit remove alredy in users
                    if (!user.getId().equals(User.getInstance().getUserId())) {
                        users.add(user);
                        // Prevents from addin the already added user to the search list
                        if(!mQuizz.getAllowed_users().containsValue(user.getId())) {
                            usersNames.add(user.getName());
                        }
                    }
                });
                navigator.onRetrieved(usersNames);
                // If it comes from edited
                setAlreadyMembers();
            });
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

    public void saveEditedQuizz(HashMap<String, Object> allowedUsers, String quizzId) {
        mQuizz.setAllowed_users(allowedUsers);
        String path = "Quizzes/" + quizzId + "/allowed_users";
        FirebaseDatabase.getInstance().getReference(path).updateChildren(allowedUsers, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                navigator.onError(databaseError.getMessage());
                return;
            }
            navigator.onSavedQuizz(allowedUsers);
        });

    }

}
