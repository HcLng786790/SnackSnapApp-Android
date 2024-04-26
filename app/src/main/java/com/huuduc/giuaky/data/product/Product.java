package com.huuduc.giuaky.data.product;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Product implements Serializable {

    public static final int FOOD = 1;
    public static final int DRINK = 2;
    public static final int SNACK = 3;
    public static final int SAUCE = 4;

    private long id;
    private String name;
    private double price;
    private int type;
    private String img;
    @SerializedName("favorite")
    private boolean isFavorite;


    public Product(){}
    public Product(long id,String name, double price, int type, String img,boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.img = img;
        this.isFavorite=isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }


    public String getPriceFormat() {
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(price);
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        switch (type){
            case FOOD:
                return "Food";
            case DRINK:
                return "Drink";
            case SNACK:
                return "Snack";
            case SAUCE:
                return "Sauce";
        }

        return null;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResoureId(Context context) {

        String fileName = this.img.substring(0, this.img.lastIndexOf('.'));
        Resources resources = context.getResources();
        int res = resources.getIdentifier(fileName, "drawable", context.getPackageName());

        return res;
    }
}
