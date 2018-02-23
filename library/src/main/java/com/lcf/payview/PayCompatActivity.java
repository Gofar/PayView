package com.lcf.payview;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcf.payview.keyboard.KeyboardView;
import com.lcf.payview.keyboard.PayInputView;
import com.lcf.payview.requestbutton.OnRequestCallback;
import com.lcf.payview.requestbutton.RequestButton;

/**
 * @author lcf
 * @date 2018/2/23 15:23
 * @since 1.0
 */
public class PayCompatActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIvClose;
    private TextView mTvForgetPsd;
    private PayInputView mPayInputView;
    private KeyboardView mKeyboardView;
    private RequestButton mRequestButton;
    private FrameLayout mLayPay;
    private LinearLayout mLayLoading;
    private LinearLayout mLayRoot;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pay_compat);
        // 全屏展示
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mIvClose = findViewById(R.id.iv_close);
        mTvForgetPsd = findViewById(R.id.tv_forget_psd);
        mPayInputView = findViewById(R.id.pay_input_view);
        mKeyboardView = findViewById(R.id.keyboard_view);
        mRequestButton = findViewById(R.id.request_button);
        mLayPay = findViewById(R.id.lay_pay);
        mLayLoading = findViewById(R.id.lay_loading);
        mLayRoot = findViewById(R.id.lay_root);

        mIvClose.setOnClickListener(this);
        mTvForgetPsd.setOnClickListener(this);
        mPayInputView.setOnClickListener(this);
        mPayInputView.setKeyboardView(mKeyboardView);
        mPayInputView.setOnPassWordInputListener(new PayInputView.OnPassWordInputListener() {
            @Override
            public void complete(String password) {
                submit(password);
            }
        });
        mRequestButton.setOnRequestCallback(new OnRequestCallback() {
            @Override
            public boolean beforeRequest() {
                return true;
            }

            @Override
            public void onRequest() {

            }

            @Override
            public void onFinish(boolean isSuccess) {
                // 延迟一秒关闭
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

        // 开始时从底部弹出
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_slide_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLayRoot.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_close) {
            close();

        } else if (i == R.id.tv_forget_psd) {

        } else if (i == R.id.pay_input_view) {
            if (mKeyboardView.getVisibility() != View.VISIBLE) {
                needShowKeyboardView(true);
            }

        }
    }

    private void submit(String psd) {
        mLayPay.setVisibility(View.GONE);
        mLayLoading.setVisibility(View.VISIBLE);
        mRequestButton.startRequest();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRequestButton.requestSuccess();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        if (mLayPay.getVisibility() == View.VISIBLE && mKeyboardView.getVisibility() == View.VISIBLE) {
            needShowKeyboardView(false);
        } else {
            close();
        }
    }

    /**
     * 是否展示KeyboardView
     *
     * @param need
     */
    private void needShowKeyboardView(boolean need) {
        if (need) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_slide_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mKeyboardView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mKeyboardView.startAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_slide_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mKeyboardView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mKeyboardView.startAnimation(animation);
        }
    }

    /**
     * 关闭Activity
     * 先隐藏PayView,再关闭
     */
    private void close() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_slide_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLayRoot.startAnimation(animation);
    }
}
