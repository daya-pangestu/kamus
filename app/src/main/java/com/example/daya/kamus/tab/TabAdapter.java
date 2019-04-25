package com.example.daya.kamus.tab;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.daya.kamus.EngFragment;
import com.example.daya.kamus.IndFragment;

public class TabAdapter extends FragmentPagerAdapter {


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EngFragment();

            case 1:
                return new IndFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "english - indonesia";
            case 1:
                return "indonesia - english";
                default:
                    return null;
        }

    }
}
