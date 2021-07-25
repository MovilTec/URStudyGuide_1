package com.example.urstudyguide_migration;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.urstudyguide_migration.Messages.MessagesFragment;
import com.example.urstudyguide_migration.Quizzes.ui.quizzes.QuizzesFragment;
import com.example.urstudyguide_migration.Social.FriendsFragment;
import com.example.urstudyguide_migration.Social.RequestFragment;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Resources res;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        res = context.getResources();
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
                return res.getString(R.string.main_menu_first);
            case 1:
                return res.getString(R.string.main_menu_second);
            case 2:
                return res.getString(R.string.main_menu_third);
            case 3:
                return res.getString(R.string.main_menu_forth);
            default:
                return null;
        }
//        return super.getPageTitle(position);
    }
}
