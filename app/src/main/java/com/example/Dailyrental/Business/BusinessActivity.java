package com.example.Dailyrental.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Dailyrental.R;
import com.example.Dailyrental.Login.LoginActivity;

public class BusinessActivity extends AppCompatActivity {
    //我的发布按钮
    private Button published;
    //    发布日租房的按钮
    private Button publish;
    //    日租房售卖详情的按钮
    private Button saledetails;

    //后退按钮
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        //初始化
        init();
    }

    private void init() {
        //获取按钮id
        published = (Button)findViewById(R.id.business_published);
        publish = (Button)findViewById(R.id.business_publish);
        saledetails = (Button)findViewById(R.id.business_saledetails);
        back = (ImageView)findViewById(R.id.customer_back);
//        为按钮设置监听
        //发布日租房信息的按钮，跳转到PublishActivity
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessActivity.this, PublishActivity.class);
                startActivity(intent);
//              销毁此Activity
                finish();
            }
        });
//        我的发布订单按钮，跳转到发布订单界面
        published.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        我的发布订单按钮，跳转到PublishedActivity
                Intent intent = new Intent(BusinessActivity.this, PublishedActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
        //        售卖详情按钮，跳转到售卖详情界面
        saledetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        售卖详情按钮，跳转到SaleDetailsActivity
                Intent intent = new Intent(BusinessActivity.this, SaleDetailsActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
//        为后退按钮设置监听,跳转到loginActivity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessActivity.this, LoginActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });

    }
}