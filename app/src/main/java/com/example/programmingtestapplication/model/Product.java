package com.example.programmingtestapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "product_table")
public class Product {
    @PrimaryKey(autoGenerate = true)
    int id;
    String productName;
    int unitInMilli,quantity,rateInTaka;
    Date date;

    public Product(String productName, int unitInMilli, int quantity, int rateInTaka, Date date) {
        this.productName = productName;
        this.unitInMilli = unitInMilli;
        this.quantity = quantity;
        this.rateInTaka = rateInTaka;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitInMilli() {
        return unitInMilli;
    }

    public void setUnitInMilli(int unitInMilli) {
        this.unitInMilli = unitInMilli;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRateInTaka() {
        return rateInTaka;
    }

    public void setRateInTaka(int rateInTaka) {
        this.rateInTaka = rateInTaka;
    }
}
