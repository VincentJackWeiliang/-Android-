package com.example.Dailyrental.Business;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Dailyrental.R;
import com.example.Dailyrental.Success.SuccessBusinessActivity;
import com.example.Dailyrental.Login.LoginActivity;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name;
    private EditText number;
    private EditText price;
    private EditText service;
    private EditText address;
    private Button button;
    private SQLiteDatabase db;
//    后退按钮
    private ImageView back;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
//        初始化界面控件
        init();
//        为确定发布按钮设置监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                获取商家输入的日租房的名字、日租房可用数量、日租房的价格、日租房提供的服务、日租房的地址。
                String str_name = name.getText().toString().trim();
                String str_number = number.getText().toString().trim();
                String str_price = price.getText().toString().trim();
                String str_service = service.getText().toString().trim();
                String str_address = address.getText().toString().trim();
//                若商家输入的日租房可用数量不是数字，提示用户日租房可用数量要输入数字，请重新输入
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(str_number);
                if(!m.matches() ){
                    Toast.makeText(PublishActivity.this, "日租房可用数量要是数字,请重新输入.", Toast.LENGTH_SHORT).show();
                }else {
//                    将商家输入的数据插入到数据库中,将商家的名字也加入进去。
                    insert(LoginActivity.name,str_name, str_number, str_price, str_service, str_address);
                    insert_Business(LoginActivity.name,str_name, str_number, str_price, str_service, str_address);
//                    插入成功，跳转到商家发布成功界面。
                    Intent intent = new Intent(PublishActivity.this, SuccessBusinessActivity.class);
                    startActivity(intent);
                    finish();//销毁此Activity
                }
            }
        });
    }

    private void init() {

        name = (EditText)findViewById(R.id.business_name);
        number = (EditText)findViewById(R.id.business_number);
        price = (EditText)findViewById(R.id.business_price);
        service = (EditText)findViewById(R.id.business_service);
        address = (EditText)findViewById(R.id.business_address);
        button = (Button)findViewById(R.id.business_btn);
        back = findViewById(R.id.business_back);
        back.setOnClickListener( this);

    }
    //插入数据,插入一条数据到数据库。
    public void insert(String business_name,String name, String number, String price, String service, String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ///为应用程序加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //接收数据库的URL,数据库用户名，用户口令，即连接数据库
                    java.sql.Connection cn= DriverManager.getConnection(url+"/data?useUnicode=true&characterEncoding=UTF-8","root","root");
                    //向information表插入数据，其中business_name字段插入的值对应第一个问号，name字段插入的值对应第二个问号，number字段插入的值对应第三个问号，price字段插入的值对应第四个问号，service字段插入的值对应第五个问号，address字段插入的值对应第六个问号。
                    String sql = "insert into data.information (business_name,name,number,price,service,address) values (?,?,?,?,?,?);";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    //通过setString给6个问好赋值.
                    pstm.setString(1, business_name);
                    pstm.setString(2, name);
                    pstm.setString(3, number);
                    pstm.setString(4, price);
                    pstm.setString(5, service);
                    pstm.setString(6, address);
                    //执行更新数据库
                    pstm.executeUpdate();
                    //关闭链接
                    cn.close();
                    //关闭访问
                    pstm.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();//开始线程
    }
    private void insert_Business(String name, String order_name, String number, String price, String service, String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ///为应用程序加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //接收数据库的URL,数据库用户名，用户口令，即连接数据库
                    java.sql.Connection cn= DriverManager.getConnection(url+"/business?useUnicode=true&characterEncoding=UTF-8","root","root");
                    //向information表插入数据，其中name字段插入的值对应第一个问号，order_name字段插入的值对应第二个问号，number字段插入的值对应第三个问号，price字段插入的值对应第四个问号，service字段插入的值对应第五个问号，address字段插入的值对应第六个问号。
                    String sql = "insert into business.information (name,order_name,number,price,service,address) values (?,?,?,?,?,?);";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    //通过setString给6个问好赋值,
                    pstm.setString(1, name);
                    pstm.setString(2, order_name);
                    pstm.setString(3, number);
                    pstm.setString(4, price);
                    pstm.setString(5, service);
                    pstm.setString(6, address);
                    //执行更新数据库
                    pstm.executeUpdate();
                    //关闭链接
                    cn.close();
                    //关闭访问
                    pstm.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();//开始线程
    }
    @Override
    public void onClick(View v) {
//        后退按钮，返回到BusinessActivity
        Intent intent = new Intent(this, BusinessActivity.class);
        startActivity(intent);
        finish();

    }
}