package com.example.bbvacontrol.uranitexpert;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    private RecyclerView mRequestsList;
    private DatabaseReference mRequestsReference;
    private View mMainView;


    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_request, container, false);

        mRequestsReference = FirebaseDatabase.getInstance().getReference().child("Friends_requests");
        mRequestsList = mMainView.findViewById(R.id.requests_RecyclerView);

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

        Users users = new Users();
        Query query = FirebaseDatabase.getInstance().getReference().child("Requested_Users").child(users.getUserID()).limitToLast(50);

        FirebaseRecyclerOptions<RequestsModelingClass> options = new FirebaseRecyclerOptions.Builder<RequestsModelingClass>()
                .setQuery(query, RequestsModelingClass.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestsModelingClass, RequestsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull RequestsModelingClass model) {
                holder.setName(model.name);
                holder.getUserImage(model.thumb_image);

//                final String user_id = getRef(position).getKey();

//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent profileIntent = new Intent(RequestFragment.this, ProfileActivity.class);
//                        profileIntent.putExtra("user_id", user_id);
//                        startActivity(profileIntent);
//
//                    }
//                });

            }

            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_friend_requests, parent, false);

                return new RequestFragment.RequestsViewHolder(view);
            }

        };

        mRequestsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

        public static class RequestsViewHolder extends RecyclerView.ViewHolder{

                View mView;

                public RequestsViewHolder(View itemView){
                    super(itemView);

                    mView = itemView;
                }
                public void setName(String name){
                    TextView userNameView = mView.findViewById(R.id.userRequest_userName);
                    userNameView.setText(name);
                }

                //Aquí es donde se cambia la imagen
                public void getUserImage(String Thumb_image){
                    CircleImageView usersImageView = mView.findViewById(R.id.userRequest_CircleImageView);
                    if(!Thumb_image.equals("default")) {
                        Picasso.get().load(Thumb_image).into(usersImageView);
                    }
                }

            }
//            @Override
//            public RequestFragment.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_own_view, parent, false);
//
//                return new RequestFragment.RequestsViewHolder(view);
//            }
        };






