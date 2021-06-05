package com.codewithharry.chatsapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithharry.chatsapp.ChatDetailActivity;
import com.codewithharry.chatsapp.Models.MessageModel;
import com.codewithharry.chatsapp.Models.Users;
import com.codewithharry.chatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {

    ArrayList<Users> list;



    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
       return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.ic_user2222).into(holder.image);
        holder.username.setText( users.getUserName());
        if(users.getStatus()==null) {
            holder.status.setText("Status: Using ConnectIn" );
        }else{
            holder.status.setText("Status: " + users.getStatus());

        }





        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid()+ users.getUserId())
                .orderByChild("timestamp")
           .limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for(DataSnapshot snapshot2 : snapshot.getChildren()){
                        String timenew =snapshot2.child("timestamp").getValue().toString();
                        Long time2 = Long.parseLong(timenew);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String newtime =  formatter.format(time2);
                         holder.usertime.setText(newtime);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid() + users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                holder.lastmessage.setText(snapshot1.child("message").getValue().toString());
                            }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("profilepic",users.getProfilepic());
                intent.putExtra("username",users.getUserName());
//                intent.putExtra("onlinestatus",users.getOnlineStatus());

                context.startActivity(intent);
//                ((Activity)context).finish();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
//        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView username , lastmessage,usertime,status;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profileimage);
            username = itemView.findViewById(R.id.Username);
            lastmessage = itemView.findViewById(R.id.lastMessage);
            usertime = itemView.findViewById(R.id.usertime);
            status = itemView.findViewById(R.id.tvstatus);



        }
    }
}
