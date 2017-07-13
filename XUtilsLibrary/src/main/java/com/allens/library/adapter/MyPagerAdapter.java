package com.allens.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by allens on 2017/7/13.
 */

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<View> mLists;

    public MyPagerAdapter(ArrayList<View> list) {
        this.mLists = list;
    }

    //填写数据源大小
    @Override
    public int getCount() {
        return mLists.size() == 0 ? 0 : mLists.size();
    }


    //返回值永远是: arg0 == arg1
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //根据下标获取对象
        View v = mLists.get(position);
        //容器里存放视图,不要的视图可以从容器中删除  ViewGroup提供的addView的方法
        container.addView(v);
        return v;  //返回实际的Object
    }

    /**
     * 销毁视图的,提高内存使用效率,在里当前页面相隔较远(隔开相邻的)的View,系统会自动回收释放内存
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        //2种删除View的方式都可以
        container.removeView((View) object);
//			View v = mLists.get(position);
//			container.removeView(v);
    }
}
