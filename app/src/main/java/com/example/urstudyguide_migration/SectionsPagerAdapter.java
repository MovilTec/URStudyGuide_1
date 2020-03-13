package com.example.urstudyguide_migration;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.urstudyguide_migration.Messages.MessagesFragment;
import com.example.urstudyguide_migration.Quizzes.ui.quizzes.QuizzesFragment;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;
            case 1:
                MessagesFragment messagesFragment= new MessagesFragment();
                return messagesFragment;
//            case 2:
//                RequestFragment requestFragment = new RequestFragment();
//                return requestFragment;
            case 2:
                QuizzesFragment quizzFragment = new QuizzesFragment();
                return quizzFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Questioners";
            case 1:
                return "Messages";
//            case 2:
//                return "Requests";
            case 2:
                return "Quizzes";
            default:
                return null;
        }
//        return super.getPageTitle(position);
    }
}
