package com.example.urstudyguide_migration.Social.Users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

import static androidx.recyclerview.widget.RecyclerView.*;

public class UsersFirebaseAdapter extends FirebaseRecyclerAdapter<UsersModelingClass, UsersFirebaseAdapter.UsersViewHolder> {

    private UserSelection userSelection;

    /**
     * Initialize a {@link Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersFirebaseAdapter(FirebaseRecyclerOptions<UsersModelingClass> options) {
        super(options);
    }

    public UsersFirebaseAdapter(FirebaseRecyclerOptions<UsersModelingClass> options, UserSelection userSelection) {
        super(options);

        this.userSelection = userSelection;
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

            holder.mView.setOnClickListener(v -> {
                userSelection.onUserSelected(user_id);
            });

        }

        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_own_view, parent, false);

            return new UsersViewHolder(view);
        }

    public interface UserSelection {
        void onUserSelected(String userID);
    }
}