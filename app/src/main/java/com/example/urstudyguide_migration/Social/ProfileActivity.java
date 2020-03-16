package com.example.urstudyguide_migration.Social;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private static int current_request_state = 0;
    private DatabaseReference mFriendRequestedDatabase;
    private AlertDialog dialog;
    private Display display;

    Users users = new Users();

    Point size = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        display = getWindowManager().getDefaultDisplay();


        final String user_id = getIntent().getStringExtra("user_id");

        mFriendRequestedDatabase = FirebaseDatabase.getInstance().getReference().child("Users_requests");

        mToolbar = findViewById(R.id.profile_tool_bar);
        setSupportActionBar(mToolbar);
        users.setBarUserTitle(user_id, mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView userName = findViewById(R.id.profile_userName_TextView);
        TextView userStatus = findViewById(R.id.profile_userStatus_textView);
        CircleImageView userImage = findViewById(R.id.profile_userImage_CircleImageView);
        final Button friendRequestButton = findViewById(R.id.profile_Requestbutton);
        final Button sendMessageButton = findViewById(R.id.profile_SendMessagebutton);

        if(user_id.equals(users.getUserID())){
            friendRequestButton.setVisibility(View.INVISIBLE);
        }else{
            users.getFriendRequestStatus(user_id, friendRequestButton, sendMessageButton, ProfileActivity.this);
        }

        users.getUserImage(user_id, userImage);
        users.getUserName(user_id, userName);
        users.getUsersStatus(user_id, userStatus);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.user_image_layout, null);
                final ImageView profileUserImage = mView.findViewById(R.id.profileUserImageView);
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                int Width = width - (width/12);
                int Height = height - (height/2);

                profileUserImage.getLayoutParams().height = Height;
                profileUserImage.getLayoutParams().width = Width;

                users.getProfileUserImageLarge(user_id, profileUserImage);

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(Width, Height);

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
