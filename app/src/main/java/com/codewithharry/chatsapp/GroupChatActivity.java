package com.codewithharry.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.codewithharry.chatsapp.Adapters.ChatAdapter;
import com.codewithharry.chatsapp.Adapters.UsersAdapter;
import com.codewithharry.chatsapp.Models.MessageModel;
import com.codewithharry.chatsapp.Models.Users;
import com.codewithharry.chatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getSupportActionBar().hide();

//        data=ChatDetailActivity.getActivityInstance().getData();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(messageModels,this);
        binding.ChatrecyclarView.setAdapter(adapter);

        final  String senderId = FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Users Group");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ChatrecyclarView.setLayoutManager(layoutManager);

        database.getReference().child("Groupchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);


                    model.setGroupmessageid(dataSnapshot.getKey());
                    messageModels.add(model);
                }
                adapter.notifyDataSetChanged();
                binding.ChatrecyclarView.smoothScrollToPosition(binding.ChatrecyclarView.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {
                String message = binding.etMessage.getText().toString();
//
//                FirebaseDatabase.getInstance().getReference().child("Users").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot snapshot2 : snapshot.getChildren()){
//                            String data = snapshot2.child("userName").getValue().toString();
//
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });




                if(message.isEmpty() ) {
                    binding.etMessage.setError("Can't send Empty Message");
                    return;
                }
                if(message.trim().length() == 0){
                    binding.etMessage.setError("Can't send Message containing only Spaces");
                    return;
                }





                MessageModel model = new MessageModel(senderId,message);


                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");



                database.getReference().child("Groupchat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {







                    }






                });
            }
        });



        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupChatActivity.this,MainActivity.class));
                finish();
            }
        });


    }




}