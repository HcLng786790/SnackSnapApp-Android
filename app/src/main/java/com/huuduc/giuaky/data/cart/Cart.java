package com.huuduc.giuaky.data.cart;

import android.content.Context;
import android.content.res.Resources;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Cart implements Serializable {

    private long id;
    private String nameSP;
    private double priceSP;
    private String imgSP;
    private int soluongSP;

    public Cart(){}

    public Cart(long id,String nameSP, double priceSP, String imgSP, int soluongSP) {
        this.id = id;
        this.nameSP = nameSP;
        this.priceSP = priceSP;
        this.imgSP = imgSP;
        this.soluongSP = soluongSP;
    }
    public String getPriceFormat() {
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(priceSP);
    }

    public String getNameSP() {
        return nameSP;
    }

    public void setNameSP(String nameSP) {
        this.nameSP = nameSP;
    }

    public double getPriceSP() {
        return priceSP;
    }

    public void setPriceSP(double priceSP) {
        this.priceSP = priceSP;
    }


    public String getImgSP() {
        return imgSP;
    }

    public void setImgSP(String imgSP) {
        this.imgSP = imgSP;
    }

    public int getSoluongSP() {
        return soluongSP;
    }

    public void setSoluongSP(int soluongSP) {
        this.soluongSP = soluongSP;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResoureId(Context context) {

        String fileName = this.imgSP.substring(0, this.imgSP.lastIndexOf('.'));
        Resources resources = context.getResources();
        int res = resources.getIdentifier(fileName, "drawable", context.getPackageName());

        return res;
    }
}
