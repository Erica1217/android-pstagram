package com.pstagram.pstagram.pstagram.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.pstagram.pstagram.pstagram.view.AlimFragment;
import com.pstagram.pstagram.pstagram.view.HomeFragment;
import com.pstagram.pstagram.pstagram.view.MyPageFragment;
import com.pstagram.pstagram.pstagram.view.SearchFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    String[] tabTitle = {"홈","검색","알림","내 정보"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new AlimFragment();
            case 3:
                return new MyPageFragment();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }
}
