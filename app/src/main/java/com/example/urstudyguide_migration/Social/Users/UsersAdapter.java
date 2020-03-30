package com.example.urstudyguide_migration.Social.Users;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.R;
import com.example.urstudyguide_migration.Social.ProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

import static androidx.recyclerview.widget.RecyclerView.*;

public class UsersAdapter extends FirebaseRecyclerAdapter<UsersModelingClass, UsersAdapter.UsersViewHolder> {


    /**
     * Initialize a {@link Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersAdapter(FirebaseRecyclerOptions<UsersModelingClass> options) {
        super(options);
    }

    public static class UsersViewHolder extends ViewHolder {
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



    @Override
    protected void onBindViewHolder(UsersViewHolder holder, int i, UsersModelingClass model) {
            holder.setName(model.name);
            holder.getStatus(model.status);
            holder.getUserImage(model.thumb_image);

            final String user_id = getRef(i).getKey();

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
//                    profileIntent.putExtra("user_id", user_id);
//                    startActivity(profileIntent);

                }
            });

        }

        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_own_view, parent, false);

            return new UsersViewHolder(view);
        }
}
