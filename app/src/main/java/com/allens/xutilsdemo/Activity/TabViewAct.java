package com.allens.xutilsdemo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.allens.library.XUtils;
import com.allens.xutilsdemo.Fragment.TabFragment;
import com.allens.xutilsdemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by allens on 2017/6/14.
 */

public class TabViewAct extends AppCompatActivity {
    @BindView(R.id.activity_tab_tab)
    TabLayout activityTabTab;
    @BindView(R.id.activity_tab_viewpager)
    ViewPager activityTabViewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabview);
        ButterKnife.bind(this);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance("1", ""));
        fragments.add(TabFragment.newInstance("2", ""));
        fragments.add(TabFragment.newInstance("3", ""));


        XUtils.create()
                .tabControl(activityTabTab, activityTabViewpager, getSupportFragmentManager(), new String[]{"1", "2", "3"}, fragments);
    }
}
