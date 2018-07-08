package com.example.bbvacontrol.uranitexpert;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


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

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FriendsModelingClass, FriendsFragment.FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendsFragment.FriendsViewHolder holder, int position, @NonNull FriendsModelingClass model) {
                holder.setName(model.name);
                holder.setStatus(model.status);
                holder.setThumb_image(model.thumb_image);

                //Obteniendo el ID del usuario solicitador
                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence options[] = new CharSequence[]{"Open Profile", "Send message"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());



                        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);
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
                Picasso.get().load(Thumb_image).
                    networkPolicy(NetworkPolicy.OFFLINE)
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
