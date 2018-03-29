package com.example.red.magazine.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.red.magazine.fragments.TellUsAboutYouFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RED on 10/3/2017.
 */

public class RegistrationFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment>  FragmentList = new ArrayList<>();
    private final List<String> list= new ArrayList<>();
    public RegistrationFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        FragmentList.add(fragment);
        list.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }


}