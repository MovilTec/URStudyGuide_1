package com.example.urstudyguide_migration.Social;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Messages.MessageChatActivity;
import com.example.urstudyguide_migration.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private View mMainView;
    private DatabaseReference mRequestsReference;
    private RecyclerView mRequestsList;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);

        mRequestsReference = FirebaseDatabase.getInstance().getReference().child("Friends_requests");
        mRequestsReference.keepSynced(true);
        mRequestsList = mMainView.findViewById(R.id.friends_ReyclerView);

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
        Query query = FirebaseDatabase.getInstance().getReference().child("Users_Relationships").child(users.getUserID());
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<FriendsModelingClass> options = new FirebaseRecyclerOptions.Builder<FriendsModelingClass>()
                .setIndexedQuery(query, dataRef, FriendsModelingClass.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FriendsModelingClass, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendsFragment.FriendsViewHolder holder, int position, @NonNull FriendsModelingClass model) {
                final String userName = model.name;
                final String userThumb_image = model.thumb_image;
                final String userImage = model.image;

                holder.setName(model.name);
                holder.setStatus(model.status);
                holder.setThumb_image(model.thumb_image);

                //Obteniendo el ID del usuario solicitador
                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence options[] = new CharSequence[]{"Open Profile", "Send message", "Check Questionnaires"};

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Select Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0:
                                        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
                                        profileIntent.putExtra("user_id", user_id);
                                        startActivity(profileIntent);
                                        break;
                                    case 1:
                                        Intent chatIntent = new Intent(getActivity(), MessageChatActivity.class);
                                        chatIntent.putExtra("user_id", user_id);
                                        chatIntent.putExtra("user_name", userName);
                                        chatIntent.putExtra("user_thumb_image", userThumb_image);
                                        chatIntent.putExtra("user_image", userImage);
                                        startActivity(chatIntent);
                                        break;
                                    case 2:
                                        break;
                                }

                            }
                        });
                        builder.show();
//                        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
//                        profileIntent.putExtra("user_id", user_id);
//                        startActivity(profileIntent);
                    }
                });
            }

            @NonNull
            @Override
            public FriendsFragment.FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_friends_container_layout, parent, false);

                return new FriendsFragment.FriendsViewHolder(view);
            }

        };

        mRequestsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FriendsViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }
        public void setName(String name){
            TextView userNameView = mView.findViewById(R.id.friendContainer_userName_textView);
            userNameView.setText(name);
        }

        public void setStatus(String name){
            TextView userStatusView = mView.findViewById(R.id.friendContainer_userStatus_textView);
            userStatusView.setText(name);
        }

        public void setUserImage(boolean online_status){
            //ImageView userOnlineView = mView.findViewById(R.id.);
            if(!online_status){

            }else{

            }
        }

        //Aquí es donde se cambia la imagen
        public void setThumb_image(final String Thumb_image){
            final CircleImageView userThumb_image = mView.findViewById(R.id.friendContainer_CircleImageView);
            if(!Thumb_image.equals("default")) {
                Picasso.get()
                        .load(Thumb_image)
                        .placeholder(R.drawable.default_user_image_1)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(userThumb_image, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(Thumb_image).into(userThumb_image);
                            }
                        });
            }
        }

    }

}
