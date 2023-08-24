package com.example.Dailyrental;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReserveDialog extends AlertDialog {
    public ReserveDialog(Context context, String title, String name, String number, String price, String service, String address, String negativeString, String positiveString) {
        super(context);
        this.title=title;
        this.name=name;
        this.number=number;
        this.price=price;
        this.service=service;
        this.address=address;
        this.negativeString=negativeString;
        this.positiveString=positiveString;

    }
    String  title; //显示的标题
    String  name;//日租房的名称。
    String  number;//剩余数量，数量为0则表示不提供。
    String  price;//日租房的价格
    String  service;//日租房提供的服务
    String  address;//日租房的地址
    String negativeString;//取消按钮
    String positiveString;//确定按钮
    OnClickBottomListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_dialog);
//        初始化界面控件
        Button negative=findViewById(R.id.negative);
        TextView title_tv=findViewById(R.id.title_tv);
        TextView name_tv=findViewById(R.id.name_tv);
        TextView number_tv=findViewById(R.id.number_tv);
        TextView price_tv=findViewById(R.id.price_tv);
        TextView service_tv=findViewById(R.id.service_tv);
        TextView address_tv=findViewById(R.id.address_tv);
        Button positive=findViewById(R.id.positive);
//        初始化界面控件的显示数据
        title_tv.setText(title);//设置标题控件的文本为自定义的title
        name_tv.setText("日租房名称:"+name);//自定义日租房名称
        number_tv.setText("剩余房间数:"+number);//自定义剩余房间数
        price_tv.setText("今日价格:"+price);//自定义今日价格
        service_tv.setText("提供的服务:"+service);//自定义提供的服务
        address_tv.setText("地址:"+address);//自定义地址
        negative.setText(negativeString);//自定义取消按钮的文本
        positive.setText(positiveString);//自定义确定按钮的文本
//        初始化界面的确定和取消监听器
//        设置“确定”按钮的点击事件的监听器
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                listener.onPositiveClick();
            }
        });
//        设置“取消”按钮的点击事件的监听器
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                listener.onNegativeClick();
            }
        });


    }
    public interface OnClickBottomListener{
        void onPositiveClick();//实现“确定”按钮的点击事件的方法
        void onNegativeClick();//实现“取消”按钮的点击事件的方法
    }
//设置”确定“”取消“按钮的回调
    public void setListener(OnClickBottomListener listener) {
        this.listener = listener;
    }
}
