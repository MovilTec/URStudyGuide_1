package com.example.urstudyguide_migration.Messages;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.Common.Models.Messages;
import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.Services.FirebasePushNotificationService;
import com.example.urstudyguide_migration.Common.Timer;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageChatActivity extends AppCompatActivity {

    private String mChatUser_name, mChatUser;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTitle, mLastSeen;
    private CircleImageView mUserImage;
    private ImageButton sendButton, addButton;
    private EditText messageText;
    private DatabaseReference mRootRef, mUserDatabase, mMessagesDatabase;
    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private String mLastKey, mPrevKey = "";

    private static final String userID = User.getInstance().getUserId();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mChatUser = getIntent().getStringExtra("user_id");
        mChatUser_name = getIntent().getStringExtra("user_name");

        mToolbar = findViewById(R.id.message_app_bar);
        setSupportActionBar(mToolbar);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

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

        sendButton = findViewById(R.id.message_sendImageButton);
        addButton = findViewById(R.id.message_addImageButton);
        messageText = findViewById(R.id.message_editText);

//        ---- Setting the Message Recycler View ---
        messageAdapter = new MessageAdapter(messagesList);

        mRecyclerView = findViewById(R.id.message_RecyclerView);
        mRefreshLayout = findViewById(R.id.message_swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(messageAdapter);
//        ---------------------------------------------
//          Loading the messages
        loadMessages();

        mTitle.setText(mChatUser_name);

        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("online")) {
                    String online = dataSnapshot.child("online").getValue().toString();
                    if (online.equals("true")) {
                        mLastSeen.setText("online");
                    } else if (!online.isEmpty()) {
                        long lastTime = Long.parseLong(online);
                        String lastSeen = Timer.getTimeAgo(lastTime, getApplicationContext());
                        mLastSeen.setText(lastSeen);
                    } else {
                        mLastSeen.setText("offline");
                    }
                }else{
                    mLastSeen.setText("offline");
                }
                final String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

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

        mRootRef.child("Users_converations").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(mChatUser)){
                    Map messageAddMap = new HashMap();
                    messageAddMap.put("seen", false);
                    messageAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map messageUserMap = new HashMap();
                    messageUserMap.put("Users_conversations/" + userID + "/" + mChatUser, messageAddMap);
                    messageUserMap.put("Users_conversations/" + mChatUser + "/" + userID, messageAddMap);

                    mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError != null){
                                Exception e = databaseError.toException();
                                Log.d("Message Log", "En error has ocurred!. ERROR CODE:" + e);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage++;

                //messagesList.clear();
                itemPos = 0;

                loadMoreMessages();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        switchSeenMessageState();
    }

    private void switchSeenMessageState() {
        String path = "Messages/" + userID + "/" + mChatUser;
        HashMap<String, Object> messageMap = new HashMap();
        messageMap.put("seen", true);
        FirebaseDatabase.getInstance().getReference(path)
                .orderByChild("from")
                .equalTo(mChatUser)
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot childDataSnapshot: dataSnapshot.getChildren()) {
                            String lastMessage = childDataSnapshot.getKey();

                            String lastMessagePath = path + "/" + lastMessage;
                            FirebaseDatabase.getInstance().getReference(lastMessagePath)
                                    .updateChildren(messageMap, (databaseError, databaseReference) -> {
                                        if(databaseError != null) {
                                            // TODO:-
                                            System.out.println(databaseError);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadMessages() {

        DatabaseReference messageRef = mRootRef.child("Messages").child(userID).child(mChatUser);
        Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Messages message  = dataSnapshot.getValue(Messages.class);

                itemPos++;
                if(itemPos == 1){
                    String messageKey = dataSnapshot.getKey();

                    mLastKey = messageKey;
                    mPrevKey = messageKey;
                }

                messagesList.add(message);
                messageAdapter.notifyDataSetChanged();

                mRecyclerView.scrollToPosition(messagesList.size() - 1);
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadMoreMessages(){

        DatabaseReference messageRef = mRootRef.child("messages").child(userID).child(mChatUser);

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Messages message = dataSnapshot.getValue(Messages.class);
                String messageKey = dataSnapshot.getKey();
                if(!mPrevKey.equals(messageKey)){
                    messagesList.add(itemPos++, message);
                }else{
                    mPrevKey = mLastKey;
                }

                if(itemPos == 1){
                    mLastKey = messageKey;
                }

                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);

                messageAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
                linearLayoutManager.scrollToPositionWithOffset(10, 0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        String Message = messageText.getText().toString();
        if(!TextUtils.isEmpty(Message)){
            messageText.setText("");

            String current_user_ref = "Messages/" + userID + "/" + mChatUser;
            String chat_user_ref = "Messages/" + mChatUser + "/" + userID;

            DatabaseReference user_message_push = mRootRef.child("Messages")
                    .child(userID).child(mChatUser).push();
            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", Message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", userID);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError != null){
                        Exception e = databaseError.toException();
                        Log.d("Message Log", "En error has ocurred!. ERROR CODE:" + e);
//                        messageText.setText("");
                    } else{
                        sendNotification(Message, mChatUser);
                    }
                }
            });
        }
    }

    private void sendNotification(String message, String recieverId) {
        RequestQueue queue = Volley.newRequestQueue(this);

        FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
        String receiver_path = "Users/" + recieverId + "/device_token";
        String path = "Users/" + userID + "/name";
        CompletableFuture<DataSnapshot> completableFuture = firebaseManager.read(path);

        completableFuture.thenCombine(firebaseManager.read(receiver_path), (sender_dataSnapshot, receiver_dataSnapshot) -> {
            // getting the data from both requests
            String device_token = (String) receiver_dataSnapshot.getValue();
            String sender_name = (String) sender_dataSnapshot.getValue();
            JSONObject data = new JSONObject();

            try {
                data.put("user_id", userID);
                data.put("user_name", sender_name);

                final String finalMessage = "Tienes un nuevo mensaje de " + sender_name + ": " + message;
                JsonObjectRequest request = FirebasePushNotificationService.getInstance().sendNotification(finalMessage, data, device_token);
                queue.add(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserDatabase.child("online").setValue(true);
    }
}
