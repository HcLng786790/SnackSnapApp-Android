package com.huuduc.giuaky.data;

import com.google.gson.annotations.SerializedName;

public class DoanhThu {

    private int thang;
    @SerializedName("doanhThu")
    private double doanhthu;

    public DoanhThu(int thang, double doanhthu) {
        this.thang = thang;
        this.doanhthu = doanhthu;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public double getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(double doanhthu) {
        this.doanhthu = doanhthu;
    }
}
