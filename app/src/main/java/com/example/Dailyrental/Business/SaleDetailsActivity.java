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
import com.example.Dailyrental.Login.LoginActivity;
import com.example.Dailyrental.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SaleDetailsActivity extends AppCompatActivity {
    private ListView mlistView;
    //用来存放数据库的数据。
    public ArrayList<Saledetails> saledetailsArrayList;
    //后退按钮
    private ImageView back;
    Handler handler= null;
    //连接数据库
    private String url="jdbc:mysql://192.168.43.98:3306";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_details);
        //设置适配器，初始化ListView控件。
        mlistView = (ListView) findViewById(R.id.lv);
        back = (ImageView)findViewById(R.id.iv_saledetails_back);
//        为后退按钮设置监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                后退按钮，返回到BusinessActivity
                Intent intent = new Intent(SaleDetailsActivity.this, BusinessActivity.class);
                startActivity(intent);
//                           销毁此Activity
                finish();
            }
        });
        //实例化ArrayList<Saledetails>。
        saledetailsArrayList = new ArrayList<Saledetails>();
        //查询数据库，根据loginActivity.name进行查询，将是本商家的数据放入数组，以备接下来构建适配器，显示。
        saledetailsFind(LoginActivity.name);


    }
    class SaleDetailsViewHolder {
        public TextView business_name, customer_name,order_name,price,service, address;
    }

    class SaleDetailsBaseAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<Saledetails> saledetailsArrayList;
        private int  id;
        private Context context;
        private LayoutInflater inflater;

        public SaleDetailsBaseAdapter(int saledetalis_item, SaleDetailsActivity saleDetailsActivity, ArrayList<Saledetails> saledetailsArrayList) {
            this.saledetailsArrayList = saledetailsArrayList;
            this.context = saleDetailsActivity;
            this.id = saledetalis_item;
            inflater = LayoutInflater.from(context);
        }

        @Override
        //获取item的总数，返回ListView Item条目的总数。
        public int getCount() {
            return saledetailsArrayList.size();
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
            SaleDetailsViewHolder holder = null;
            if (convertView == null) {
//                加载publish_item.xml布局文件
                convertView = inflater.inflate(id, null);
                holder = new SaleDetailsViewHolder();
//                初始化控件
                holder.business_name = (TextView) convertView.findViewById(R.id.tv_saledetails_business_name);
                holder.customer_name = (TextView) convertView.findViewById(R.id.tv_saledetails_customer_name);
                holder.order_name = (TextView) convertView.findViewById(R.id.tv_saledetails_order_name);
                holder.price = (TextView) convertView.findViewById(R.id.tv_saledetails_price);
                holder.service = (TextView) convertView.findViewById(R.id.tv_saledetails_service);
                holder.address = (TextView) convertView.findViewById(R.id.tv_saledetails_address);
                convertView.setTag(holder);
            } else {
                holder = (SaleDetailsViewHolder) convertView.getTag();
            }
            Saledetails saledetails = saledetailsArrayList.get(position);
            if (saledetails != null) {
//                设置文本
                holder.business_name.setText("商家名字:"+saledetails.getBusiness_name());
                holder.customer_name.setText("客户的名字:"+saledetails.getCustomer_name());
                holder.order_name.setText("日租房的名字："+saledetails.getOrder_name());
                holder.price.setText("价格:" + saledetails.getPrice());
                holder.service.setText(saledetails.getService());
                holder.address.setText("地址:" + saledetails.getAddress());
            }
            return convertView;
        }
    }
    //查询数据,将表中的关于当前用户的数据读取出来，放入数组里面，供后面进行显示。
    public void saledetailsFind(String quaryName) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url+"/saledetalis?useUnicode=true&characterEncoding=UTF-8", "root", "root");
                    String sql = "select business_name, customer_name,order_name,price,service, address from saledetalis.information";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String business_name = rs.getString("business_name");
                        String customer_name = rs.getString("customer_name");
                        String order_name = rs.getString("order_name");
                        String price = rs.getString("price");
                        String service = rs.getString("service");
                        String address = rs.getString("address");
                        if (business_name.equals(quaryName)) {
                            // 生成Saledetails对象
                            Saledetails saledetails = new Saledetails(business_name, customer_name, order_name, price, service, address);
                            //将数据库的一条数据存放在saledetails里面，再将saledetails放到saledetailsArrayList里面，
                            saledetailsArrayList.add(saledetails);
                        }
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    System.out.println("连接数据库成功");
                    handler.sendMessage(handler.obtainMessage(0, saledetailsArrayList));
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
                private ArrayList<Saledetails> saledetailsArrayList;

                public void handleMessage(Message msg)
                {

                    if(msg.what==0)
                    {
                        //msg.obj是获取handler发送信息传来的数据
                        saledetailsArrayList = (ArrayList<Saledetails>) msg.obj;
                        System.out.println(saledetailsArrayList);
                        //给ListView绑定数据
                        BinderListData(saledetailsArrayList);
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
    public void BinderListData(ArrayList<Saledetails> saledetailsArrayList)
    {
        //创建adapter对象
        SaleDetailsBaseAdapter adapter = new SaleDetailsBaseAdapter(R.layout.saledetalis_item, this, saledetailsArrayList);
        //将Adapter绑定到listview中
        mlistView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}