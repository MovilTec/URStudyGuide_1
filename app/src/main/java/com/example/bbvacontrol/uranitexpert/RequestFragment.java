package com.example.bbvacontrol.uranitexpert;


import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        final Users users = new Users();
        Query query = FirebaseDatabase.getInstance().getReference().child("Requested_Users").child(users.getUserID());
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<RequestsModelingClass> options = new FirebaseRecyclerOptions.Builder<RequestsModelingClass>()
                .setIndexedQuery(query, dataRef, RequestsModelingClass.class)
//                .setQuery(query, RequestsModelingClass.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestsModelingClass, RequestsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull RequestsModelingClass model) {
                holder.setName(model.name);
                holder.getUserImage(model.thumb_image);

                Button ConfirmRequestButton = holder.mView.findViewById(R.id.userRequest_confirmButton);
                Button DeclineRequestButton = holder.mView.findViewById(R.id.userRequest_declineButton);
                //Obteniendo el ID del usuario solicitador
                final String user_id = getRef(position).getKey();

                ConfirmRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users.acceptUserRequest(user_id, getActivity());
                        Toast.makeText(getActivity(), "Se ha ACEPTADO la solicitud de amistad", Toast.LENGTH_LONG).show();
                    }
                });

                DeclineRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        users.eliminateUserRequest(users.getUserID(), user_id, getActivity());
                        Toast.makeText(getActivity(), "Se ha DECLINADO la solicitud de amistad", Toast.LENGTH_LONG).show();
                    }
                });

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

        }






