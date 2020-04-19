package com.example.urstudyguide_migration.Common.Helpers;

import android.os.Build;

import com.google.firebase.database.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import androidx.annotation.RequiresApi;

public class FirebaseManager  {
    private FirebaseDatabase database;

    public FirebaseManager(FirebaseDatabase database) {
        this.database = database;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<DataSnapshot> read(String path) {
        final CompletableFuture<DataSnapshot> promise = new CompletableFuture();

        database.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                promise.complete(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                promise.completeExceptionally(databaseError.toException());
            }
        });

        return promise;
    }

    public String generateId(String path) {
        return database.getReference(path).push().getKey();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Void> write(String path, Map<String, Object> writeMap) {
        final CompletableFuture<Void> promise = new CompletableFuture<>();
        database.getReference(path).updateChildren(writeMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    promise.complete(null);
                } else {
                    promise.completeExceptionally(databaseError.toException());
                }
            }
        });

        return promise;
    }
}
