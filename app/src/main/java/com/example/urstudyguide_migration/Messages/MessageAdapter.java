package com.example.urstudyguide_migration.Messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.urstudyguide_migration.Common.Models.Messages;
import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.Timer;
import com.example.urstudyguide_migration.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter {

    private List<Messages> mMessagesList;
    private static final int VIEW_TYPE_MESSAGE_SENT  = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    Users users = new Users();
    Timer timer = new Timer();

    public MessageAdapter(List<Messages> mMessagesList){
        this.mMessagesList = mMessagesList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_single_layout_sender, parent, false);
            return new SentMessageViewHolder(v);
        }else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_single_layout, parent, false);
            return new RecivedMessageViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Messages c = mMessagesList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageViewHolder) holder).bind(c);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((RecivedMessageViewHolder)holder).bind(c);
        }
    }


    @Override
    public int getItemViewType(int position) {
        Messages message = mMessagesList.get(position);
        if(message.getFrom().equals(users.getUserID())){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
        //return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    public class RecivedMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText, hourText;
        public CircleImageView profileImage;

        public RecivedMessageViewHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.single_message_textView);
            hourText = view.findViewById(R.id.message_single_hour_textView);
            //profileImage = view.findViewById(R.id.message_single_CircleImageView);
        }
        void bind(Messages message){
            messageText.setText(message.getMessage());
            String time = timer.getDataTimeStamp(message.getTime());
            hourText.setText(time);
        }

    }

    public class SentMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        public SentMessageViewHolder(View view){
            super(view);

            messageText = view.findViewById(R.id.single_message_sender_textView);
        }
        void bind(Messages message){
            messageText.setText(message.getMessage());
        }

    }

}