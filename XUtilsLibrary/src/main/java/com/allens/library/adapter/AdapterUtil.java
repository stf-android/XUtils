package com.allens.library.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allens on 2017/7/13.
 */

public class AdapterUtil {

    private LinearLayoutManager layout;


    private static AdapterUtil adapterUtil;


    private AdapterUtil(Context context) {
        layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    public static AdapterUtil create(Context context) {
        if (adapterUtil == null) {
            adapterUtil = new AdapterUtil(context);
        }
        return adapterUtil;
    }

    public <T> com.zhy.adapter.abslistview.CommonAdapter adapter(Context context, int layoutId, List<T> dataList, GridView gridview,
                                                                 final OnAdapterListener.OnAbListListener<T> listener) {
        com.zhy.adapter.abslistview.CommonAdapter<T> adapter = new com.zhy.adapter.abslistview.CommonAdapter<T>(context, layoutId, dataList) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, T item, int position) {
                listener.convert(viewHolder, item, position);
            }
        };
        gridview.setAdapter(adapter);
        return adapter;
    }

    public <T> com.zhy.adapter.abslistview.CommonAdapter adapter(Context context, int layoutId, List<T> dataList, ListView listView, final OnAdapterListener.OnAbListListener<T> listener) {
        com.zhy.adapter.abslistview.CommonAdapter<T> adapter = new com.zhy.adapter.abslistview.CommonAdapter<T>(context, layoutId, dataList) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, T item, int position) {
                listener.convert(viewHolder, item, position);
            }
        };
        listView.setAdapter(adapter);
        return adapter;
    }

    public <T> CommonAdapter adapter(Context context, int layoutId, List<T> dataList, RecyclerView recyclerView, final OnAdapterListener.OnRvListener<T> listener) {
        CommonAdapter<T> commonAdapter = new CommonAdapter<T>(context, layoutId, dataList) {
            @Override
            protected void convert(ViewHolder holder, T t, int position) {
                listener.convert(holder, t, position);
            }
        };
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(commonAdapter);
        return commonAdapter;
    }


    public LinearLayoutManager getLinearLayoutManager() {
        return layout;
    }


    public ViewPagerAdapter adapter(FragmentManager fragmentManager, ViewPager viewPager, ArrayList<Fragment> fragments) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        return viewPagerAdapter;
    }

    public MyPagerAdapter adapter(ViewPager viewPager, ArrayList<View> viewArrayList) {
        MyPagerAdapter viewPagerAdapter = new MyPagerAdapter(viewArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        return viewPagerAdapter;
    }

    public MyPagerAdapter adapter(Context context, ViewPager viewPager, ArrayList<Integer> imgResIds) {
        ArrayList mLists = new ArrayList<>();
        for (int i = 0; i < imgResIds.size(); i++) {
            ImageView iv = new ImageView(context);
            //给ImageView设置layout_width,layout_height的属性
            //1.通过LayoutParams才能设置 (只要属性是layout开头的,必须通过LayoutParams设置)
            //layout_weight,layout_margin
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT); //第一个参数width,第二个参数:height
            iv.setLayoutParams(lp);
            //根据下标获取实际的图片
            iv.setImageResource(imgResIds.get(i));
            //sacleType的参数 (如果图片是自适应sacleType没有任何作用,)
            //75*75  480*800   如果设置match_parent或者定值写死的时候scaleType对图片要重新定义
            //因为图片等比缩放,和屏幕大小有差别,默认情况下不能做到全部撑满,修改scaleType属性
            iv.setScaleType(ImageView.ScaleType.FIT_XY); //等比例缩放以后,根据控件大小进行拉伸
            //存放到容器中
            mLists.add(iv);
        }
        MyPagerAdapter viewPagerAdapter = new MyPagerAdapter(mLists);
        viewPager.setAdapter(viewPagerAdapter);
        return viewPagerAdapter;
    }

}
