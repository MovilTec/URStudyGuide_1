package com.example.bbvacontrol.uranitexpert;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private String mChatUser_name, mChatUser_image, mCharUser_thumb_image, mChatUser;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private RecyclerView mRecyclerView;
    private TextView mTitle, mLastSeen;
    private CircleImageView mUserImage;
    private DatabaseReference mRootRef;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mChatUser = getIntent().getStringExtra("user_id");
        mChatUser_name = getIntent().getStringExtra("user_name");
        mChatUser_image = getIntent().getStringExtra("user_image");
        mCharUser_thumb_image = getIntent().getStringExtra("user_thumb_image");

        mToolbar = findViewById(R.id.message_app_bar);
        setSupportActionBar(mToolbar);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowCustomEnabled(true);

        //getSupportActionBar().setTitle(mChatUser_name);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = inflater.inflate(R.layout.message_bar_layout, null);

        mActionBar.setCustomView(action_bar_view);
//      ------ Custom Action Bar Items -------

        mTitle = findViewById(R.id.message_bar_userName_textView);
        mLastSeen = findViewById(R.id.message_bar_lastSeen_textView);
        mUserImage = findViewById(R.id.message_bar_CircleImageView);
        mTitle.setText(mChatUser_name);

        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("online")) {
                    String online = dataSnapshot.child("online").getValue().toString();
                    if (online.equals("true")) {
                        mLastSeen.setText("online");
                    } else if (!online.isEmpty()) {
                        mLastSeen.setText(online);
                    } else {
                        mLastSeen.setText("offline");
                    }
                }else{
                    mLastSeen.setText("offline");
                }
                final String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                if(!thumb_image.equals("default")) {
                    Picasso.get().load(thumb_image).
                            networkPolicy(NetworkPolicy.OFFLINE)
                            .into(mUserImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(thumb_image).into(mUserImage);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
