package com.lcf.payview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv_start_pay);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PayCompatActivity.class));
            }
        });

        if (PayHandlerImpl.class.isAnnotationPresent(PayModule.class)) {
            PayModule module = PayHandlerImpl.class.getAnnotation(PayModule.class);
            String value = module.value();
            textView.setText(value);
        }

        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SetPayActivity.class));
            }
        });
    }
}
