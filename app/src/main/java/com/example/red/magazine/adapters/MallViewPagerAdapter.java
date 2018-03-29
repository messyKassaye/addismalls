package com.example.red.magazine.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RED on 10/6/2017.
 */

public class MallViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> fragment_name=new ArrayList<>();

    public MallViewPagerAdapter(FragmentManager manager){
        super(manager);

    }

    public void  addFrag(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragment_name.add(title);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return fragment_name.get(position);
    }
}
