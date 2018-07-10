package com.example.bbvacontrol.uranitexpert;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private View mMainView;
    private DatabaseReference mUsersDatabase, mMessageDatabase;
    private RecyclerView mRequestsList;

    Users users = new Users();

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_messages, container, false);

        mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("Messages").child(users.getUserID());
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mRequestsList = mMainView.findViewById(R.id.fragment_messager_RecyclerView);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mRequestsList.setHasFixedSize(true);
        mRequestsList.setLayoutManager(linearLayoutManager);

        // Inflate the layout for this fragment
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        startListening();
    }

    private void startListening() {

        final Users users = new Users();
        Query query = FirebaseDatabase.getInstance().getReference().child("Users_conversations").child(users.getUserID()).orderByChild("timestamp");

        FirebaseRecyclerOptions<MessageModelingClass> options = new FirebaseRecyclerOptions.Builder<MessageModelingClass>()
                .setQuery(query, MessageModelingClass.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MessageModelingClass, MessagesFragment.MessagesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MessagesFragment.MessagesViewHolder holder, int position, @NonNull MessageModelingClass model) {
                //Obteniendo el ID del usuario solicitador
                final String list_user_id = getRef(position).getKey();

                Query lastMessageQuery = mMessageDatabase.child(list_user_id).limitToLast(1);

                lastMessageQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String data = dataSnapshot.child("message").getValue().toString();
                        String from = dataSnapshot.child("from").getValue().toString();
                        String time = dataSnapshot.child("time").getValue().toString();
                        holder.setMessage(data, from);
                        holder.setTime(time);
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

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb_image = dataSnapshot.child("thumb_image").getValue().toString();


                        holder.setName(userName);
                        holder.setUserImage(userThumb_image);

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent messageIntent = new Intent(getActivity(), MessageActivity.class);
                                messageIntent.putExtra("user_id", list_user_id);
                                messageIntent.putExtra("user_name", userName);
                                startActivity(messageIntent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MessagesFragment.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);

                return new MessagesFragment.MessagesViewHolder(view);
            }

        };

        mRequestsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder{
        private DatabaseReference mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        View mView;

        public MessagesViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }

        public void setTime(String time){
            Timer timer = new Timer();
            final TextView timeView = mView.findViewById(R.id.message_time_textView);
            String messageTime = timer.getDataTimeStamp(Long.parseLong(time));
            timeView.setText(messageTime);
        }

        public void setMessage(final String message, String from){
            final TextView messageView = mView.findViewById(R.id.message_lastMessage_textView);
            Users users = new Users();
            if(from.equals(users.getUserID())) {
                messageView.setText(message);
            }else{
                mUsersDatabase.child(from).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String userName = dataSnapshot.getValue().toString();
                        messageView.setText(message);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        public void setName(String name){
            TextView userNameView = mView.findViewById(R.id.message_userName_textView);
            userNameView.setText(name);
        }

        //Aquí es donde se cambia la imagen
        public void setUserImage(String Thumb_image){
            CircleImageView usersImageView = mView.findViewById(R.id.message_userImage);
            if(!Thumb_image.equals("default")) {
                Picasso.get().load(Thumb_image).into(usersImageView);
            }
        }

    }

}
