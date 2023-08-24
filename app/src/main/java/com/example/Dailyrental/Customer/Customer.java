package com.example.Dailyrental.Customer;

public class Customer {
    String name ; //订购人的名字
    String order_name;//日租房名字
    String service; //日租房的服务
    String address;//日租房的地址
    public Customer(){}

    public Customer(String name, String order_name,String service, String address) {
        this.name = name;
        this.order_name = order_name;
        this.service = service;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", order_name='" + order_name + '\'' +
                ", service='" + service + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
