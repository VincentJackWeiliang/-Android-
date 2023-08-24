package com.example.Dailyrental.Login;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Dailyrental.Business.BusinessActivity;
import com.example.Dailyrental.Customer.CustomerActivity;
import com.example.Dailyrental.DisplayData.Data;
import com.example.Dailyrental.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private RadioGroup radioGroup;
    private String login_name;
    public static String name;
    public ArrayList<User> data;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";
    private String user="root";
    private String password="root";
    PreparedStatement statement=null;
    Statement stat=null;
    Connection conn=null;
    private Handler handler=null;
    private boolean match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化视图中的控件对象 initView()
        initView();
        data = new ArrayList<User>();
    }

    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        radioGroup = (RadioGroup) findViewById(R.id.rdg);
        //利用setOnCheckedChangeListener()为RadioGroup设置监听事件。
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /*把checkedId的内容与RadioGroup的id进行比较，若是商家则记录为商家，若为客户则记录为客户。保存下来，通过这个来判断是商家还是
                客户，然后做相应的处理。*/
                if (checkedId == R.id.rbtn) {
                    login_name = "商家";
                } else {
                    login_name = "客户";
                }
            }
        });

        // 设置点击事件监听器，点击登陆
        mBtLoginactivityLogin.setOnClickListener(this);
        //点击注册
        mTvLoginactivityRegister.setOnClickListener(this);
        //线程，读取数据库的信息，进行匹配，若成功则登陆。
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/user", "root", "root");
                    String sql = "select username,password from user.information";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
//                                读取相应列的信息。
                        String username = rs.getString("username");
                        String password = rs.getString("password");
//                                将读取的信息保存
                        data.add(new User(username, password));
                    }
//关闭
                    cn.close();
                    st.close();
                    rs.close();
                    System.out.println("连接数据库成功");
                    handler.sendMessage(handler.obtainMessage(0, data));
                } catch (ClassNotFoundException e) {
                    System.out.println("连接数据库失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            //开启线程
            new Thread(runnable).start();
            //handler与线程之间的通信及数据处理
            handler = new Handler() {

                @SuppressWarnings("unchecked")

                public void handleMessage(Message msg) {

                    if (msg.what == 0) {
                        //msg.obj是获取handler发送信息传来的数据
                        data = (ArrayList<User>) msg.obj;
                        System.out.println(data);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 点击的到注册按钮
            case R.id.tv_loginactivity_register:
//                跳转到注册界面，并且注销当前界面
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.bt_loginactivity_login:
//                从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
                name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
//                进行匹配验证,先判断一下用户名密码是否为空，

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
//                    再进而for循环判断是否与数据库中的数据相匹配
                    match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
//                             一旦匹配，立即将match = true
                            match = true;
                            break;
                        } else {
//                            否则 一直匹配到结束
                            match = false;
                        }
                    }
                    if (match) {
//                        登录成功之后，进行页面跳转：此时若是商家登陆，则跳转到BusinessActivity，吐司商家登录成功
                        if (login_name.equals("商家")) {
                            Toast.makeText(this, "商家登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(this, BusinessActivity.class);
                            startActivity(intent1);
//                           销毁此Activity
                            finish();
                        } else {
//                            若是客户登陆，则跳转到CustomerActivity，吐司客户登录成功
                            Toast.makeText(this, "客户登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(this, CustomerActivity.class);
                            startActivity(intent2);
//                            销毁此Activity
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                if(!match){
                    Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
        }
}

    //连接数据库
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (Exception  e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return conn;
    }
}



