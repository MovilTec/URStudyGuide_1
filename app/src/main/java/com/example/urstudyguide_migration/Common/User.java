package com.example.urstudyguide_migration.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private static User shared = null;
    public static User getInstance() {
        if (shared == null)
            shared = new User();

        return shared;
    }

    public void setUserID(Context context, String userID) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userID", userID);
        editor.commit();
    }

    public String getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getString("userID", null);
    }
}
