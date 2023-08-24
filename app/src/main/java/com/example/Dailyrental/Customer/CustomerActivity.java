package com.example.Dailyrental.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Dailyrental.R;
import com.example.Dailyrental.DisplayData.DisplayActivity;
import com.example.Dailyrental.Login.LoginActivity;

public class CustomerActivity extends AppCompatActivity {
//我的订单按钮
    private Button order;
//    显示日租房的按钮
    private Button display;
    //后退按钮
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        //初始化
        init();

    }

    private void init() {
        //获取按钮id
        display = (Button)findViewById(R.id.customer_display_btn);
        order = (Button)findViewById(R.id.customer_order_btn);
        back = (ImageView)findViewById(R.id.customer_back);
//        为按钮设置监听
        //显示日租房信息的按钮，跳转到displayActivity
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, DisplayActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
//        我的订单按钮，跳转到我的订单界面
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               跳转到CustomerOrderActivity
                Intent intent = new Intent(CustomerActivity.this, CustomerOrderActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
//        为后退按钮设置监听,跳转到loginActivity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });

    }
}