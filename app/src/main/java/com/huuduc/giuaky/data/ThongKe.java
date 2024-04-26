package com.huuduc.giuaky.data;

import com.google.gson.annotations.SerializedName;

public class ThongKe {

    private String productName;
    @SerializedName("quantity")
    private int sum;

    public ThongKe(String productName, int sum) {
        this.productName = productName;
        this.sum = sum;
    }

    public ThongKe() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
