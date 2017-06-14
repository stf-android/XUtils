package com.allens.library.TabControl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Allens on 2016/9/4.
 */
public class RgToFmUtils {
    private int showindex;
    private int hideindex;
    private List<Fragment> fragmentList;
    private FragmentManager supportFragmentManager;
    private int id;
    private RadioGroup radioGroup;

    public void showTabToFragment(List<Fragment> fragmentList, RadioGroup radioGroup, FragmentManager supportFragmentManager, int id) {
        this.radioGroup = radioGroup;
        this.id = id;
        this.fragmentList = fragmentList;
        this.supportFragmentManager = supportFragmentManager;
        ((RadioButton) radioGroup.getChildAt(showindex)).setChecked(true);//初始化选中第一个
        showFragment(showindex, hideindex);//初始化碎片

        initsetOnClickListener();
    }

    /**
     * 作用：监听
     * name: Allens
     * created at 2016/9/4 10:23
     */
    private void initsetOnClickListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                radioButton.setChecked(true);
                int i = group.indexOfChild(radioButton);
                showFragment(i, hideindex);
                hideindex = i;
            }
        });
    }

    /**
     * 作用：显示碎片的逻辑
     * name: Allens
     * created at 2016/9/4 10:23
     */
    private void showFragment(int showindex, int hideindex) {
        Fragment showfragment = fragmentList.get(showindex);
        Fragment hidefragment = fragmentList.get(hideindex);
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        if (!showfragment.isAdded()) {
            ft.add(id, showfragment);
        }
        if (showindex == hideindex) {
            ft.show(showfragment);
        } else {
            ft.show(showfragment);
            ft.hide(hidefragment);
        }
        ft.commit();
    }

}
