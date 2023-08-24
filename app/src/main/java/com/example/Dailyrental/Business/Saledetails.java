package com.example.Dailyrental.Business;

public class Saledetails {
    private String business_name;//商家的名字
    private String customer_name; //客户的名字
    private String order_name; //订单的名字
    private String price; //价格
    private String service;  //服务
    private String address; //地址

    public Saledetails(){}
    public Saledetails(String business_name, String customer_name, String order_name, String price, String service, String address) {
        this.business_name = business_name;
        this.customer_name = customer_name;
        this.order_name = order_name;
        this.price = price;
        this.service = service;
        this.address = address;

    }

    @Override
    public String toString() {
        return "Saledetails{" +
                "business_name='" + business_name + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", order_name='" + order_name + '\'' +
                ", price='" + price + '\'' +
                ", service='" + service + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
