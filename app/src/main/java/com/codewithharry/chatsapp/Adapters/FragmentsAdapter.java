package com.codewithharry.chatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codewithharry.chatsapp.Fragmaents.CallsFragments;
import com.codewithharry.chatsapp.Fragmaents.ChatsFragments;
import com.codewithharry.chatsapp.Fragmaents.StatusFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new ChatsFragments();
            case 1: return new StatusFragment();
//            case 2: return new CallsFragments();
            default:return new ChatsFragments();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        String title = null ;


//            title = "USERS";

        if(position==0){
            title = "USERS";

        }
        if(position==1){
            title = "USERS ACTIVITIES";
        }
//        if(position==2){
//            title = "CALLS";
//        }

        return title;
    }
}
