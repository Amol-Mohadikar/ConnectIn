package com.codewithharry.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.codewithharry.chatsapp.Adapters.ChatAdapter;
import com.codewithharry.chatsapp.Models.MessageModel;
import com.codewithharry.chatsapp.Models.Users;
import com.codewithharry.chatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.common.stats.StatsUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

//import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

//    static ChatDetailActivity INSTANCE;
//    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        INSTANCE = this;
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final  String senderId = auth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String username = getIntent().getStringExtra("username");
        String profilepic = getIntent().getStringExtra("profilepic");


//        String onlinestatus = getIntent().getStringExtra("onlinestatus");

//        data = username;


        binding.userName.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.ic_user2222).into(binding.profileimage);



//        if(onlinestatus.equals("online")){
//            binding.onlinestat.setText(onlinestatus);
//        }else {
//            Long datetime = Long.parseLong(onlinestatus);
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//            String newtime =  formatter.format(datetime);
//            binding.onlinestat.setText("Last seen at: " + newtime);
//
//        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatDetailActivity.this,MainActivity.class));
                finish();
            }
        });






        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(messageModels,this,receiverId);
        binding.ChatrecyclarView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ChatrecyclarView.setLayoutManager(layoutManager);





        final String senderroom = senderId+receiverId;
        final String recieverroom = receiverId+senderId;

        database.getReference().child("Chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    model.setMessageId(dataSnapshot.getKey());

                    messageModels.add(model);
                }
                adapter.notifyDataSetChanged();
                binding.ChatrecyclarView.smoothScrollToPosition(binding.ChatrecyclarView.getAdapter().getItemCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        database.getReference().child("Users").orderByChild("uid").equalTo(receiverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String stat = ds.child("onlineStatus").getValue().toString();

                    if(stat.equals("online")){
                        binding.onlinestat.setText(stat);
                    }else {
                        Long datetime = Long.parseLong(stat);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String newtime =  formatter.format(datetime);
                        binding.lastseen.setText("Last seen at: " + newtime);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = binding.etMessage.getText().toString();

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

                database.getReference().child("Chats").child(senderroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        database.getReference().child("Chats").child(recieverroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                });

            }
        });





    }

    private void  checkOnlineStatus( String status){
        HashMap< String , Object> hashMap = new HashMap<>();
        hashMap.put("onlineStatus",status);
        database.getReference().child("Users").child(auth.getUid()).updateChildren(hashMap);
    }

    @Override
    protected void onStart () {
        checkOnlineStatus("online");
        super.onStart();
    }

    @Override
    protected void onPause () {

        String timestamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timestamp);

        super.onPause();


    }

    @Override
    protected void onResume() {
        checkOnlineStatus("online");
        super.onResume();
    }


    //    public static ChatDetailActivity getActivityInstance()
//    {
//        return INSTANCE;
//    }
//
//    public String getData()
//    {
//        return this.data;
//    }
}