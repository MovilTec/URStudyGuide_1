package com.example.bbvacontrol.uranitexpert;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> mMessagesList;

    Users users = new Users();

    public MessageAdapter(List<Messages> mMessagesList){
        this.mMessagesList = mMessagesList;
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);//null;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView profileImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.single_message_textView);
            profileImage = view.findViewById(R.id.message_single_CircleImageView);

        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        String currentUser = users.getUserID();
        Messages c = mMessagesList.get(position);
        String from_user = c.getFrom();
        //Configurando los cambios en el diseño del mensaje
        if(from_user.equals(currentUser)){
            holder.messageText.setBackgroundColor(Color.parseColor("#BDBDBD"));
            holder.messageText.setTextColor(R.color.colorPrimary);
//            holder.messageText.setHo
        }else{

        }
        holder.messageText.setText(c.getMessage());
    }


    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }


}