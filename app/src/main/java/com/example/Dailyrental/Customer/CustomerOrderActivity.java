package com.example.Dailyrental.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.example.Dailyrental.DisplayData.Data;
import com.example.Dailyrental.DisplayData.DisplayActivity;
import com.example.Dailyrental.R;
import com.example.Dailyrental.Login.LoginActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerOrderActivity extends AppCompatActivity {

    private ListView mlistView;
    //用来存放数据库的数据。
    public ArrayList<Customer> customerArrayList;
    //后退按钮
    private ImageView back;
    Handler handler= null;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        //设置适配器，初始化ListView控件。
        mlistView = (ListView) findViewById(R.id.lv);
        back = (ImageView)findViewById(R.id.iv_order_back);
//        为后退按钮设置监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                跳转到CustomerActivity
                Intent intent = new Intent(CustomerOrderActivity.this, CustomerActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
        //实例化ArrayList<Customer>。
        customerArrayList = new ArrayList<Customer>();
        //查询数据库，将数据放入数组，以备接下来构建适配器，显示。
        orderFind(LoginActivity.name);



    }
    class OrderViewHolder {
        public TextView name,order_name, service, address;
    }

    class OrderBaseAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<Customer> customers;
        private int  id;
        private Context context;
        private LayoutInflater inflater;
        public OrderBaseAdapter(int order_item, CustomerOrderActivity customerOrderActivity, ArrayList<Customer> customers) {
            this.customers = customers;
            this.context = customerOrderActivity;
            this.id = order_item;
            inflater = LayoutInflater.from(context);
        }

        @Override
        //获取item的总数，返回ListView Item条目的总数。
        public int getCount() {
            return customerArrayList.size();
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
            OrderViewHolder holder = null;
            if (convertView == null) {
//                加载shop_item.xml布局文件
                convertView =inflater.inflate(id, null);
                holder = new OrderViewHolder();
//                初始化控件
                holder.name = (TextView) convertView.findViewById(R.id.tv_order_name);
                holder.order_name = (TextView) convertView.findViewById(R.id.tv_order_order_name);
                holder.service = (TextView) convertView.findViewById(R.id.tv_order_service);
                holder.address = (TextView) convertView.findViewById(R.id.tv_order_address);
                convertView.setTag(holder);
            } else {
                holder = (OrderViewHolder) convertView.getTag();
            }
            Customer customer = customerArrayList.get(position);
            if (customer != null) {
//                设置文本
                holder.name.setText("客户名字"+customer.getName());
                holder.order_name.setText("日租房名字:"+customer.getOrder_name());
                holder.service.setText(customer.getService());
                holder.address.setText("地址:" + customer.getAddress());
            }
            return convertView;
        }
    }
    //查询数据,将表中的关于当前用户的数据读取出来，放入数组里面，供后面进行显示。
    public void orderFind(String quaryName) {

        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/customer?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "select name,order_name,service,address from customer.information";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String order_name = rs.getString("order_name");
                        String service = rs.getString("service");
                        String address = rs.getString("address");
                        if (name.equals(quaryName)) {
                            // 生成Customer对象
                            Customer customer = new Customer(name, order_name, service, address);
                            //将数据库的一条数据存放在customer里面，再将customer放到customerArrayList里面，
                            customerArrayList.add(customer);
                        }
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    System.out.println("连接数据库成功");
                    handler.sendMessage(handler.obtainMessage(0, customerArrayList));
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
                private ArrayList<Customer> customers;

                public void handleMessage(Message msg)
                {

                    if(msg.what==0)
                    {
                        //msg.obj是获取handler发送信息传来的数据
                        customers = (ArrayList<Customer>) msg.obj;
                        System.out.println(customers);
                        //给ListView绑定数据
                        BinderListData(customers);
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
    public void BinderListData(ArrayList<Customer> customers)
    {
        //创建adapter对象
        OrderBaseAdapter adapter = new OrderBaseAdapter(R.layout.order_item, this, customers);
        //将Adapter绑定到listview中
        mlistView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}