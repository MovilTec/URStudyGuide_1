package com.example.urstudyguide_migration.Quizzes.ui.quizzdetail;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.urstudyguide_migration.Common.Helpers.FirebaseManager;
import com.example.urstudyguide_migration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.urstudyguide_migration.R.drawable.default_user_image_1;

public class QuizzDetailUsersRecyclerAdapter extends RecyclerView.Adapter<QuizzDetailUsersRecyclerAdapter.mViewHolder> {

    private List<String> allowedUsers;
    private UserResponder userResponder;
    private Drawable drawable;

    public QuizzDetailUsersRecyclerAdapter(List<String> allowedUsers, UserResponder userResponder, Drawable draw) {
        this.userResponder = userResponder;
        this.allowedUsers = allowedUsers;
        allowedUsers.add("add_more");
        drawable = draw;
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        RelativeLayout relativeLayout;
        TextView tvCount;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quizz_detail_profile_image);
            relativeLayout = itemView.findViewById(R.id.quizz_detail_relative);
            tvCount = itemView.findViewById(R.id.quizz_detail_tvCount);
        }
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizz_detail_user_item_adapter, parent, false);
        QuizzDetailUsersRecyclerAdapter.mViewHolder vh = new QuizzDetailUsersRecyclerAdapter.mViewHolder(imageLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        if(position == (allowedUsers.size() - 1)) {
            holder.imageView.setImageDrawable(drawable);
            holder.imageView.setCircleBackgroundColor(Color.parseColor("#303f9f"));
            holder.imageView.setOnClickListener(v -> {
                userResponder.onUserAddAction();
            });
            return;
        }
        String userId = allowedUsers.get(position);
        FirebaseManager firebaseManager = new FirebaseManager(FirebaseDatabase.getInstance());
        String path = "Users/" + userId + "/thumb_image";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CompletableFuture<DataSnapshot> asyncTask = firebaseManager.read(path);
            asyncTask.thenAccept(dataSnapshot -> {
                String imageUri = dataSnapshot.getValue().toString();
            if(!imageUri.equals("default")) {
                Picasso.get()
                        .load(imageUri)
                        .placeholder(default_user_image_1)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.imageView);
            } else {
                Picasso.get().load(default_user_image_1);
            }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allowedUsers.size();
    }

    public interface UserResponder {
        void onUserAddAction();
    }

}
