package com.allens.library.Screen;

import android.graphics.Bitmap;

/**
 * Created by allens on 2017/6/11.
 */

public interface Screen {
    interface OnScreenWidthAndHeightListener {
        void onWidthAndHeigth(int width, int heigth);
    }

    interface OnSnapOutListener {
        void onSnapOut(Bitmap bitmap);
    }

    interface OnSnapInListener{
        void onSnapIn(Bitmap bitmap);
    }
}
