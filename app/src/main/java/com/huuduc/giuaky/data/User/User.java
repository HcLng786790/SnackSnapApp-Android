package com.huuduc.giuaky.data.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String email;
    private Long cartId;
    private String phoneNumber;
    private String name;

    private String img;


    public User() {
    }

    public User(Long id, String email, Long cartId, String phoneNumber, String name,String img) {
        this.id = id;
        this.email = email;
        this.cartId = cartId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.img=img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getResoureId(Context context) {

        String fileName = this.img.substring(0, this.img.lastIndexOf('.'));
        Resources resources = context.getResources();

        int res = resources.getIdentifier(fileName, "drawable", context.getPackageName());

        return res;
    }

}
