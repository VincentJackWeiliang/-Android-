package com.example.Dailyrental.Success;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Dailyrental.Business.PublishActivity;
import com.example.Dailyrental.R;
import com.example.Dailyrental.Login.LoginActivity;

public class SuccessBusinessActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_business);
//        初始化控件
        initView();
    }
    private void initView() {
        // 初始化控件对象
        Button mBtMainLogout = findViewById(R.id.bt_main_logout);
        // 绑定点击监听器
        mBtMainLogout.setOnClickListener(this);
    }

    public void onClick(View view) {
//        点击返回登陆按钮，返回到登陆界面
        if (view.getId() == R.id.bt_main_logout) {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
        //        点击返回按钮，返回到发布界面
        if (view.getId() == R.id.success_business_back) {
            Intent intent2 = new Intent(this, PublishActivity.class);
            startActivity(intent2);
            finish();
        }
    }
}