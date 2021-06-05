package com.codewithharry.chatsapp.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.codewithharry.chatsapp.ChatDetailActivity;
import com.codewithharry.chatsapp.Models.MessageModel;
import com.codewithharry.chatsapp.R;
import com.codewithharry.chatsapp.SettingsActivity;
import com.codewithharry.chatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.core.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatAdapter extends RecyclerView.Adapter {



    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;
    String data;
//    int list;

    public ChatAdapter(String data) {
        this.data = data;
    }

    int Sender_view_type = 1;
    int reciever_view_type = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == Sender_view_type){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return  new senderviewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return  Sender_view_type;
        }
        else{
            return reciever_view_type;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);







            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context).setTitle("One to one chat").setMessage(" Delete this message ?")
                            .setPositiveButton("Delete for me", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    String sender = auth.getUid() + recId;
                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(sender).child(messageModel.getMessageId()).child("message")
                                            .setValue("Ø You deleted this message Ø");

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return false;
                }
            });


        if(messageModel.getuId().equals(FirebaseAuth.getInstance().getUid())){

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Users Group").setMessage(" Delete this message ?")
                        .setPositiveButton("Delete for Everyone", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                String sender = auth.getUid() + recId;
                                FirebaseDatabase.getInstance().getReference().child("Groupchat").child(messageModel.getGroupmessageid()).child("message")
                                        .setValue("Ø Deleted message Ø");
                            }





                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();




            }
                });}




















        if(holder.getClass()== senderviewHolder.class){
            ((senderviewHolder) holder).sendermsg.setText(messageModel.getMessage());
            Long time = messageModel.getTimestamp();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String newtime =  formatter.format(time);
            ((senderviewHolder) holder).sendertime.setText(newtime);





        }
        else{
            ((RecieverViewHolder) holder).recievermsg.setText(messageModel.getMessage());



            Long stime = messageModel.getTimestamp();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String snewtime =  formatter.format(stime);
            ((RecieverViewHolder) holder).recieverTime.setText(snewtime);
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends  RecyclerView.ViewHolder {

        TextView recievermsg , recieverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recievermsg= itemView.findViewById(R.id.recieverText);
            recieverTime= itemView.findViewById(R.id.recieverTime);

        }
    }

    public  class
    senderviewHolder extends RecyclerView.ViewHolder {

        TextView sendermsg , sendertime;
        public senderviewHolder(@NonNull View itemView) {
            super(itemView);

            sendermsg= itemView.findViewById(R.id.senderText);
            sendertime= itemView.findViewById(R.id.senderTime);

        }
    }




}
