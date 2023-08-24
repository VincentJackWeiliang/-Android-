package com.example.Dailyrental.Business;

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

import com.example.Dailyrental.Customer.Customer;
import com.example.Dailyrental.Customer.CustomerOrderActivity;
import com.example.Dailyrental.R;
import com.example.Dailyrental.Login.LoginActivity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PublishedActivity extends AppCompatActivity {
    private ListView mlistView;
    //用来存放数据库的数据。
    public ArrayList<Business> businessArrayList;
    //后退按钮
    private ImageView back;
    Handler handler= null;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        //设置适配器，初始化ListView控件。
        mlistView = (ListView) findViewById(R.id.lv);
        back = (ImageView)findViewById(R.id.iv_published_back);
//        为后退按钮设置监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                后退按钮，返回到BusinessActivity
                Intent intent = new Intent(PublishedActivity.this, BusinessActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
        //实例化ArrayList<Business>。
        businessArrayList = new ArrayList<Business>();
        //查询数据库，根据loginActivity.name进行查询，将是本用户的数据放入数组，以备接下来构建适配器，显示。
        publishedFind(LoginActivity.name);



    }
    class BusinessViewHolder {
        public TextView name, order_name,number,price,service, address;
    }

    class BusinessBaseAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<Business> businesses;
        private int  id;
        private Context context;
        private LayoutInflater inflater;

        public BusinessBaseAdapter(int publish_item, PublishedActivity publishedActivity, ArrayList<Business> businesses) {
            this.businesses = businesses;
            this.context = publishedActivity;
            this.id = publish_item;
            inflater = LayoutInflater.from(context);
        }

        @Override
        //获取item的总数，返回ListView Item条目的总数。
        public int getCount() {
            return businessArrayList.size();
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
            BusinessViewHolder holder = null;
            if (convertView == null) {
//                加载publish_item.xml布局文件
                convertView = inflater.inflate(id, null);
                holder = new BusinessViewHolder();
//                初始化控件
                holder.name = (TextView) convertView.findViewById(R.id.tv_business_name);
                holder.order_name = (TextView) convertView.findViewById(R.id.tv_business_order_name);
                holder.number = (TextView) convertView.findViewById(R.id.tv_business_number);
                holder.price = (TextView) convertView.findViewById(R.id.tv_business_price);
                holder.service = (TextView) convertView.findViewById(R.id.tv_business_service);
                holder.address = (TextView) convertView.findViewById(R.id.tv_business_address);
                convertView.setTag(holder);
            } else {
                holder = (BusinessViewHolder) convertView.getTag();
            }
            Business business = businessArrayList.get(position);
            if (business != null) {
//                设置文本
                holder.name.setText("商家名字:"+business.getName());
                holder.order_name.setText("发布日租房的名字:"+business.getOrder_name());
                holder.number.setText("日租房的剩余数量："+business.getNumber());
                holder.price.setText("价格:"+business.getPrice());
                holder.service.setText(business.getService());
                holder.address.setText("地址:" + business.getAddress());
            }
            return convertView;
        }
    }
    //查询数据,将表中的关于当前用户的数据读取出来，放入数组里面，供后面进行显示。
    public void publishedFind(String quaryName) {
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/business?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "select name, order_name,number,price,service, address from business.information";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String order_name = rs.getString("order_name");
                        String number = rs.getString("number");
                        String price = rs.getString("price");
                        String service = rs.getString("service");
                        String address = rs.getString("address");
                        if (name.equals(quaryName)) {
                            // 生成Business对象
                            Business business= new Business(name, order_name,number,price,service, address);
                            //将数据库的一条数据存放在business里面，再将business放到businessArrayList里面，
                            businessArrayList.add(business);
                        }
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    System.out.println("连接数据库成功");
                    handler.sendMessage(handler.obtainMessage(0, businessArrayList));
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
                private ArrayList<Business> businesses;

                public void handleMessage(Message msg)
                {

                    if(msg.what==0)
                    {
                        //msg.obj是获取handler发送信息传来的数据
                        businesses = (ArrayList<Business>) msg.obj;
                        System.out.println(businesses);
                        //给ListView绑定数据
                        BinderListData(businesses);
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
    public void BinderListData(ArrayList<Business> businesses)
    {
        //创建adapter对象
        BusinessBaseAdapter adapter = new BusinessBaseAdapter(R.layout.publish_item, this, businesses);
        //将Adapter绑定到listview中
        mlistView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}