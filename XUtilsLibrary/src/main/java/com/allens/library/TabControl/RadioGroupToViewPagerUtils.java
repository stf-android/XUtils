package com.allens.library.TabControl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;


/**
 * Created by Allens on 2016/9/8.
 */
public class RadioGroupToViewPagerUtils {

    private List<Fragment> mFragmentList;
    private FragmentManager mManager;
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private List<View> mViewList;

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/14 下午1:35
     * @方法作用： tablayout view
     */
    public void showTabView(TabLayout mTabLayout, ViewPager viewPager, FragmentManager manager, String mTitleList[], List<Fragment> mFragmentList) {
        MyAdapter adapter = new MyAdapter(manager, mFragmentList, mTitleList);
        viewPager.setAdapter(adapter);
        //绑定
        mTabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 作用：传入的是pageradapter
     * name: Allens
     * created at 2016/9/8 9:45
     */
    public void showRadioToViewPager(RadioGroup radioGroup, ViewPager viewPager, List<View> viewList) {
        this.mViewList = viewList;
        this.mRadioGroup = radioGroup;
        this.mViewPager = viewPager;
        mViewPager.setAdapter(new MyPagerAdapter());
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        initsetOnClickLinstener();
    }

    /**
     * 作用：传入的是fragmentpageradapter
     * name: Allens
     * created at 2016/9/8 9:45
     */
    public void showRadioToViewPager(RadioGroup radioGroup, ViewPager viewPager, FragmentManager manager, List<Fragment> fragmentList) {
        this.mFragmentList = fragmentList;
        this.mRadioGroup = radioGroup;
        this.mManager = manager;
        this.mViewPager = viewPager;
        mViewPager.setAdapter(new MyFragmentPagerAdapter(mManager));
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        initsetOnClickLinstener();
    }


    /**
     * 作用：相互监听
     * name: Allens
     * created at 2016/9/8 9:46
     */
    private void initsetOnClickLinstener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页数在移动过程中调用的方法
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页数发生改变的时候调用该地方
            @Override
            public void onPageSelected(int position) {
                RadioButton rb = (RadioButton) mRadioGroup.getChildAt(position);
                rb.setChecked(true);
            }

            //页数改变时候滚动状态的监听
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                int i = group.indexOfChild(rb);
                mViewPager.setCurrentItem(i);
            }
        });
    }

    /**
     * 作用：Adapter
     * name: Allens
     * created at 2016/9/20 12:29
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = mViewList.get(position);
            container.removeView(view);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        private String mTitleList[];

        public MyAdapter(FragmentManager fm, List<Fragment> list, String mTitleList[]) {
            super(fm);
            this.list = list;
            this.mTitleList = mTitleList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList[position];
        }
    }

}
