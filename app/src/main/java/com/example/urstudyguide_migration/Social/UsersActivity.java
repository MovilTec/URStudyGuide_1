package com.example.urstudyguide_migration.Social;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolBar = findViewById(R.id.users_app_tool_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mUsersList = findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        startListening();

}

 public void startListening(){
 Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);

        FirebaseRecyclerOptions<UsersModelingClass> options = new FirebaseRecyclerOptions.Builder<UsersModelingClass>()
                .setQuery(query, UsersModelingClass.class)
                .build();


        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersModelingClass, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UsersModelingClass model) {
                holder.setName(model.name);
                holder.getStatus(model.status);
                holder.getUserImage(model.thumb_image);

                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);

                    }
                });

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_own_view, parent, false);

                return new UsersViewHolder(view);
            }
        };

        mUsersList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }
        public void setName(String name){
            TextView userNameView = mView.findViewById(R.id.users_userName);
            userNameView.setText(name);
        }

        public void getStatus(String status){
            TextView userStatusView = mView.findViewById(R.id.users_userStatus);
            userStatusView.setText(status);
        }

        //Aquí es donde se cambia la imagen
        public void getUserImage(String Thumb_image){
            CircleImageView usersImageView = mView.findViewById(R.id.users_circleImageView);
            if(!Thumb_image.equals("default")) {
                Picasso.get().load(Thumb_image).into(usersImageView);
            }
        }

    }

}
