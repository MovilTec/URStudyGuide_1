package com.example.bbvacontrol.uranitexpert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String user_id = getIntent().getStringExtra("user_id");

        mToolbar = findViewById(R.id.profile_tool_bar);
        //setActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle("UserName");
        users.setBarUserTitle(user_id, mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView userName = findViewById(R.id.profile_userName_TextView);
        TextView userStatus = findViewById(R.id.profile_userStatus_textView);
        CircleImageView userImage = findViewById(R.id.profile_userImage_CircleImageView);

        users.getUserImage(user_id, userImage);
        users.getUserName(user_id, userName);
        users.getUsersStatus(user_id, userStatus);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
