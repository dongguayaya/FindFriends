package com.dongua.framework.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * PagerAdapter的基类
 */
public class BasePageAdapter extends PagerAdapter {

    private List<View> mList;

    public BasePageAdapter(List<View> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        ((ViewPager)container).addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        super.destroyItem(container, position, object);
        ((ViewPager)container).removeView(mList.get(position));
    }
}
