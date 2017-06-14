package com.allens.library.RxBinding;

/**
 * Created by allens on 2017/6/10.
 */

public interface OnRxBind {
    interface OnRxBindingListener {
        void OnClickListener();
    }

    interface OnRxBindingBooleanListener {
        void OnClickListener(Boolean mBoolean);
    }

    interface OnRxBindListViewListener {
        void OnClickListener(Integer integer);
    }
}
