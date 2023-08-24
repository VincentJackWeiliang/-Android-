package com.example.Dailyrental.DisplayData;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Dailyrental.Customer.CustomerActivity;
import com.example.Dailyrental.R;
import com.example.Dailyrental.ReserveDialog;
import com.example.Dailyrental.Success.SuccessUserActivity;
import com.example.Dailyrental.Login.LoginActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mlistView;
    //用来存放数据库的数据。
    public ArrayList<Data> datalist;
    //后退按钮
    private ImageView back;
    private Handler handler=null;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mlistView=(ListView)findViewById(R.id.lv);
        back = (ImageView) findViewById(R.id.iv_display_back);
        //给后退按钮设置监听
        back.setOnClickListener(this);
        //实例化ArrayList<Data>。
        datalist = new ArrayList<Data>();
        find();
    }

    @Override
    public void onClick(View v) {
        //跳转到登陆界面
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
//                           销毁此Activity
        finish();
    }

    class ViewHolder {
        public TextView business_name, name, number, price, service, address;
    }

    class MyBaseAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Data> datalist;
        private int  id;
        private Context context;
        private LayoutInflater inflater;
        public MyBaseAdapter(int item, DisplayActivity displayActivity, ArrayList<Data> data) {
            this.datalist = data;
            this.context = displayActivity;
            this.id = item;
            inflater = LayoutInflater.from(context);
        }

        @Override
        //获取item的总数，返回ListView Item条目的总数。
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
//                加载shop_item.xml布局文件
                convertView =inflater.inflate(id, null);
                holder = new ViewHolder();
//                初始化控件
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.price = (TextView) convertView.findViewById(R.id.tv_price);
                holder.number = (TextView) convertView.findViewById(R.id.tv_number);
                holder.service = (TextView) convertView.findViewById(R.id.tv_service);
                holder.address = (TextView) convertView.findViewById(R.id.tv_address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Data data = datalist.get(position);
            if (data != null) {
//                设置文本
                holder.name.setText(data.getName());
                holder.price.setText("今日价格:" + data.getPrice());
                holder.number.setText("剩余空房:" + data.getNumber());
                holder.service.setText(data.getService());
                holder.address.setText("地址:" + data.getAddress());
            }
            //每个Item的点击事件
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出对话框，对话框显示该Item的详细信息，若点击确定则修改数据库数据并且跳转到预定成功界面。若点击取消界面则关闭对话框。
                    final ReserveDialog dialog = new ReserveDialog(DisplayActivity.this, "你订购这间房吗？",
                            data.getName(), data.getNumber(), data.getPrice(), data.getService(), data.getAddress(), "我再看看", "确定"
                    );
                    dialog.setListener(new ReserveDialog.OnClickBottomListener() {

                        @Override
                        public void onPositiveClick() {
//点击确定按钮，则根据id，更新数据库的数据，将可用的日租房数量减少1个。
                            updata(data.getId(), Integer.parseInt(data.getNumber()) - 1);
//                            客户订单数据库插入一条记录
                            insert(LoginActivity.name, data.getName(), data.getService(), data.getAddress());
                            //                          售卖详情数据库插入一条记录
                            insert_saledetails(data.getBusiness_name(), LoginActivity.name, data.getName(), data.getPrice(), data.getService(), data.getAddress());
//                            更新商家发布订单的数据库中剩余日租房数量
                            updata_Business(data.getBusiness_name(), Integer.parseInt(data.getNumber()) - 1);
//                            更新成功后跳转到用户预定成功界面。
                            Intent intent = new Intent(DisplayActivity.this, SuccessUserActivity.class);
                            startActivity(intent);
                            finish();//销毁此Activity
                            //关闭对话框
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeClick() {
                            //关闭对话框
                            dialog.dismiss();
                        }
                    });
//                    回调ReserveDialog的OnCreate()方法
                    dialog.show();

                }
            });
            return convertView;
        }
    }

    //插入数据,插入一条数据到售卖详情表。
    private void insert_saledetails(String business_name, String customer_name, String order_name, String price, String service, String address) {
        //开一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ///为应用程序加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //接收数据库的URL,数据库用户名，用户口令，即连接数据库
                    java.sql.Connection cn = DriverManager.getConnection(url+"/saledetalis?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    //向information表插入数据，其中business_name字段插入的值对应第一个问号，customer_name字段插入的值对应第二个问号，order_name字段插入的值对应第三个问号，price字段插入的值对应第四个问号，service字段插入的值对应第五个问号，address字段插入的值对应第六个问号。
                    String sql = "insert into saledetalis.information (business_name,customer_name,order_name,price,service,address) values (?,?,?,?,?,?);";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    //通过setString给6个问好赋值.
                    pstm.setString(1, business_name);
                    pstm.setString(2, customer_name);
                    pstm.setString(3, order_name);
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


    //插入数据,插入一条数据到用户订单表。
    public void insert(String name, String order_name, String service, String address) {
        //开一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ///为应用程序加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //接收数据库的URL,数据库用户名，用户口令，即连接数据库
                    java.sql.Connection cn = DriverManager.getConnection(url+"/customer?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    //向information表插入数据，其中name字段插入的值对应第一个问号，order_name字段插入的值对应第二个问号，service字段插入的值对应第三个问号，address字段插入的值对应第四个问号
                    String sql = "insert into customer.information (name,order_name,service,address) values (?,?,?,?);";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    //通过setString给4个问好赋值。
                    pstm.setString(1, name);
                    pstm.setString(2, order_name);
                    pstm.setString(3, service);
                    pstm.setString(4, address);
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

    //查询数据,将表中的所有数据读取出来，供后面进行显示。
    public void find() {
         Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection cn = DriverManager.getConnection(url+"/data?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "select id,business_name,name,number,price,service,address from data.information";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String business_name = rs.getString("business_name");
                        String name = rs.getString("name");
                        String number = rs.getString("number");
                        String price = rs.getString("price");
                        String service = rs.getString("service");
                        String address = rs.getString("address");
                        if((number.matches("[0-9]+"))&&(Integer.parseInt(number)>0)) {
                            datalist.add(new Data(id, business_name, name, number, price, service, address));
                        }
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    System.out.println("连接数据库成功");
                    handler.sendMessage(handler.obtainMessage(0, datalist));
                } catch (ClassNotFoundException e) {
                    System.out.println("连接数据库失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        try
        {
            //开启线程
            new Thread(runnable).start();
            //handler与线程之间的通信及数据处理
            handler=new Handler()
            {

                @SuppressWarnings("unchecked")
                private ArrayList<Data> data;

                public void handleMessage(Message msg)
                {

                    if(msg.what==0)
                    {
                        //msg.obj是获取handler发送信息传来的数据
                        data = (ArrayList<Data>) msg.obj;
                        System.out.println(data);
                        //给ListView绑定数据
                        BinderListData(data);
                    }
                }
            };
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    } 
    //绑定数据
    public void BinderListData(ArrayList<Data> data)
    {
        //创建adapter对象
        MyBaseAdapter adapter = new MyBaseAdapter(R.layout.shop_item, this, data);
        //将Adapter绑定到listview中
        mlistView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //修改数据。
    public void updata(String id, int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/data?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "update data.information set number = ? where id = ?;";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    pstm.setString(1, Integer.toString(number));
                    pstm.setString(2, id);
                    pstm.executeUpdate();
                    cn.close();
                    pstm.close();
                    System.out.println("连接数据库成功");
                } catch (ClassNotFoundException e) {
                    System.out.println("连接数据库失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updata_Business(String quaryName, int number) {
        System.out.println(quaryName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/business?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "update business.information set number = ? where name = ?;";
                    PreparedStatement pstm = cn.prepareStatement(sql);
                    pstm.setString(1, Integer.toString(number));
                    pstm.setString(2, quaryName);
                    pstm.executeUpdate();
                    cn.close();
                    pstm.close();
                    System.out.println("连接数据库成功");
                } catch (ClassNotFoundException e) {
                    System.out.println("连接数据库失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
