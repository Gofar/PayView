package com.lcf.payview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.lcf.payview.keyboard.KeyboardPopupView;
import com.lcf.payview.keyboard.PayInputView;

/**
 * @author lcf
 * @date 2018/2/26 11:38
 * @since 1.0
 */
public class SetPayActivity extends AppCompatActivity {
    private LinearLayout mLayRoot;
    private PayInputView mPayInputView1;
    private PayInputView mPayInputView2;
    private KeyboardPopupView mPopView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay);
        mLayRoot = findViewById(R.id.lay_root);
        mPayInputView1 = findViewById(R.id.pay_input_view1);
        mPayInputView2 = findViewById(R.id.pay_input_view2);

        mPopView = new KeyboardPopupView(SetPayActivity.this);
        mPayInputView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopView.show(mLayRoot, mPayInputView1);
            }
        });
        mPayInputView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopView.show(mLayRoot, mPayInputView2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPopView.isShowing()) {
            mPopView.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
