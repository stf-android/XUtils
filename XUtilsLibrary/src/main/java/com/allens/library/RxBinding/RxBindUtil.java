package com.allens.library.RxBinding;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by allens on 2017/6/5.
 */

public class RxBindUtil {


//    private static RxBindUtil instance;

//    public static RxBindUtil newInstance() {
//        if (null == instance) {
//            instance = new RxBindUtil();
//        }
//        return instance;
//    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/5 上午10:17
     * @方法作用： Rxbinding
     */
    public void click(View view, final OnRxBind.OnRxBindingListener onRxBinding) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        onRxBinding.OnClickListener();
                    }
                });
    }

    public void clicklong(View view, final OnRxBind.OnRxBindingListener onRxBinding) {
        RxView.longClicks(view)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        onRxBinding.OnClickListener();
                    }
                });
    }

    public void clickCheckBox(CheckBox view, final OnRxBind.OnRxBindingBooleanListener rxBinding) {
        RxCompoundButton.checkedChanges(view)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        rxBinding.OnClickListener(aBoolean);
                    }
                });
    }


    public void clickListView(ListView listView, final OnRxBind.OnRxBindListViewListener onRxBindListView) {
        RxAdapterView.itemClicks(listView)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        onRxBindListView.OnClickListener(integer);
                    }
                });
    }

    public void clickLongListView(ListView listView, final OnRxBind.OnRxBindListViewListener onRxBindListView) {
        RxAdapterView.itemLongClicks(listView)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        onRxBindListView.OnClickListener(integer);
                    }
                });
    }

}
