package com.allens.library.adapter;


import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by allens on 2017/6/22.
 */

public interface OnAdapterListener {

    interface OnRvListener<T> {
        void convert(ViewHolder viewHolder, T item, int position);
    }
    interface OnAbListListener<T> {
        void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, T item, int position);
    }
}
