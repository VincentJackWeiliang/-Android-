package com.example.Dailyrental.DisplayData;

public class Data {
    private String id;    //得到日租房的id。
    private String business_name;//商家名字
    private String name ; //日租房的名称。
    private String number; //剩余数量，数量为0则表示不提供。
    private String price ; //价格
    private String service;  //服务
    private String address ; //地址

    public Data(){}

    public Data(String id,String business_name,String name, String number, String price, String service, String address) {
        this.id = id;
        this.business_name = business_name;
        this.name = name;
        this.number = number;
        this.price = price;
        this.service = service;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPrice() {
        return price;
    }

    public String getService() {
        return service;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", business_name='" + business_name + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", price='" + price + '\'' +
                ", service='" + service + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
