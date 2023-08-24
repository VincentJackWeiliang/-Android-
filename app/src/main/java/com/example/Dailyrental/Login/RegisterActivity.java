package com.example.Dailyrental.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Dailyrental.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtRegisteractivityRegister;
    private ImageView mIvRegisteractivityBack;
    private EditText Username;
    private EditText Password1;
    private EditText Password2;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";
    private String user = "root";
    private String password = "root";
    PreparedStatement statement = null;
    Statement stat = null;
    Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化视图中的控件对象 initView()
        initView();
    }

    private void initView() {
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        Username = findViewById(R.id.et_registeractivity_username);
        Password1 = findViewById(R.id.et_registeractivity_password1);
        Password2 = findViewById(R.id.et_registeractivity_password2);
//        为后退按钮设置监听
        mIvRegisteractivityBack.setOnClickListener(this);
//        为注册按钮设置监听
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码
                String username = Username.getText().toString().trim();
                String password = Password2.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    //  将输入的数据增加到数据库中，吐司用户注册成功，跳转到登陆界面，
                    //开一个子线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ///为应用程序加载驱动
                                Class.forName("com.mysql.jdbc.Driver");
                                //接收数据库的URL,数据库用户名，用户口令，即连接数据库
                                java.sql.Connection cn= DriverManager.getConnection(url+"/user","root","root");
                                //向information表插入数据，其中username字段插入的值对应第一个问号，password字段插入的值对应第二个问号。
                                String sql = "insert into user.information (username,password) values (?,?);";
                                PreparedStatement pstm = cn.prepareStatement(sql);
                                //通过setString给2个问好赋值
                                pstm.setString(1, username);
                                pstm.setString(2, password);
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

                    Toast.makeText(this, "用户注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                } else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
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

