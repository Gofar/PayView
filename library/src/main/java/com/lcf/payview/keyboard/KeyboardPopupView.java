package com.lcf.payview.keyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lcf.payview.R;

/**
 * @author lcf
 * @date 2018/2/9 17:34
 * @since 1.0
 */
public class KeyboardPopupView extends PopupWindow {
    private KeyboardView mKeyboardView;

    public KeyboardPopupView(Context context) {
        this(context, null);
    }

    public KeyboardPopupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardPopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mKeyboardView = new KeyboardView(context);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mKeyboardView.setLayoutParams(lp);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(mKeyboardView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.WindowAnimationStyle);
    }

    public void show(View root, PayInputView view) {
        view.setKeyboardView(mKeyboardView);
        if (isShowing()) {
            return;
        }
        showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }
}
