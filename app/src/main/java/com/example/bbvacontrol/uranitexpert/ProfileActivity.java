package com.example.bbvacontrol.uranitexpert;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private static int current_request_state = 0;
    private DatabaseReference mFriendRequestedDatabase;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String user_id = getIntent().getStringExtra("user_id");

        mFriendRequestedDatabase = FirebaseDatabase.getInstance().getReference().child("Friends_requests");

        mToolbar = findViewById(R.id.profile_tool_bar);
        setSupportActionBar(mToolbar);
        users.setBarUserTitle(user_id, mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView userName = findViewById(R.id.profile_userName_TextView);
        TextView userStatus = findViewById(R.id.profile_userStatus_textView);
        CircleImageView userImage = findViewById(R.id.profile_userImage_CircleImageView);
        final Button friendRequestButton = findViewById(R.id.profile_Requestbutton);

        if(user_id.equals(users.getUserID())){
            friendRequestButton.setVisibility(View.INVISIBLE);
        }else{
            users.getFriendRequestStatus(user_id, friendRequestButton);
        }

        users.getUserImage(user_id, userImage);
        users.getUserName(user_id, userName);
        users.getUsersStatus(user_id, userStatus);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        friendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ProfileActivity.this, "current friend state " + current_request_state,Toast.LENGTH_LONG).show();
                friendRequestButton.setEnabled(false);
                mProgressDialog = new ProgressDialog(ProfileActivity.this);
                mProgressDialog.setTitle("Sending Request");
                mProgressDialog.setMessage("The Request is sending, please wait until it is done");
                mProgressDialog.setCanceledOnTouchOutside(false);

                users.sendFriendRequest(current_request_state, ProfileActivity.this, mFriendRequestedDatabase, mProgressDialog, user_id, friendRequestButton);
            }
        });

    }

    public void changeRequestState(int request_state){
        current_request_state = request_state;
    }

}
