package com.example.Dailyrental.Business;

public class Business {
    private String name;//商家的名字
    private String order_name; //发布的日租房名字
    private String number; //剩余数量，数量为0则表示不提供。
    private String price; //价格
    private String service;  //服务
    private String address; //地址

    public Business() {
    }

    public Business(String name, String order_name, String number, String price, String service, String address) {
        this.name = name;
        this.order_name = order_name;
        this.number = number;
        this.price = price;
        this.service = service;
        this.address = address;
    }


    @Override
    public String toString() {
        return "Business{" +
                "name='" + name + '\'' +
                ", Order_name='" + order_name + '\'' +
                ", number='" + number + '\'' +
                ", price='" + price + '\'' +
                ", service='" + service + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
