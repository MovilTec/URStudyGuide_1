package com.example.bbvacontrol.uranitexpert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bbvacontrol.uranitexpert.Messages.MessagesFragment;
import com.example.bbvacontrol.uranitexpert.Quizzes.ui.quizzes.QuizzesFragment;

class SectionsPagerAdapter extends FragmentPagerAdapter{

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
            case 2:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 3:
                // TODO:- Add the quizes Activity!
                QuizzesFragment quizzFragment = new QuizzesFragment();
                return quizzFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Questioners";
            case 1:
                return "Messages";
            case 2:
                return "Requests";
            case 3:
                return "Quizzes";
            default:
                return null;
        }
//        return super.getPageTitle(position);
    }
}
