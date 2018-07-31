package com.example.suhirtha.randomadventure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 7/27/18.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentNames = new ArrayList<>();

    public ViewPageAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragmentNames.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentNames.get(position);
    }

    public void addFragment(Fragment newFragment, String newName){
        fragments.add(newFragment);
        fragmentNames.add(newName);
    }
}