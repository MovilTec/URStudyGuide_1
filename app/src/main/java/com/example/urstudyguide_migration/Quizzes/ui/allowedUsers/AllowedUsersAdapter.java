package com.example.urstudyguide_migration.Quizzes.ui.allowedUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Models.UsersModelingClass;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AllowedUsersAdapter extends RecyclerView.Adapter<AllowedUsersAdapter.mViewHolder> {

    private List<UsersModelingClass> users = new ArrayList();

    public AllowedUsersAdapter() {

    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_friends_container_layout, parent, false);
        AllowedUsersAdapter.mViewHolder vh = new AllowedUsersAdapter.mViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.status.setText(users.get(position).getStatus());

        String Thumb_image = users.get(position).getThumb_image();

        if(!Thumb_image.equals("default")) {
            Picasso.get()
                    .load(Thumb_image)
                    .placeholder(R.drawable.default_user_image_1)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.userImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(Thumb_image).into(holder.userImage);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView name, status;
        public CircleImageView userImage;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.friendContainer_userName_textView);
            status = itemView.findViewById(R.id.friendContainer_userStatus_textView);
            userImage = itemView.findViewById(R.id.friendContainer_CircleImageView);
        }
    }

    public void appendUser(UsersModelingClass user) {
        users.add(user);
        notifyItemInserted(users.size());
    }

    public HashMap<String, Object> getAllowedUsers() {
        HashMap<String, Object> allowedUsers = new HashMap();
        allowedUsers.put("author", User.getInstance().getUserId());
        for(UsersModelingClass user: users) {
            allowedUsers.put(user.getName(), user.getId());
        }

        return allowedUsers;
    }

}
