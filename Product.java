package com.example.appassignment;

public class Product {
    public  String names;
    public  String dobs;
    public String des;
    public int iconResId;
    private int quantity;

    public Product(String names, String dobs,String des,int iconResId) {
        this.names = names;
        this.dobs= dobs;
        this.des=des;
        this.iconResId=iconResId;
        this.quantity=1;
    }
    public  String getName() {
        return names;
    }

    public  String getDobs() {
        return dobs;
    }
    public String getDes(){
        return des;
    }

    public int getIconResId(){return iconResId;}
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
