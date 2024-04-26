package com.huuduc.giuaky.data.orders;

import com.google.gson.annotations.SerializedName;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.data.cart.Cart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Orders implements Serializable {

    public static final int DELIVERY_TYPE = 1;
    public static final int PICKUP_TYPE = 2;

    public static final long CONFIRM = 1;
    public static final long COOKING = 2;
    public static final long DELIVERING = 3;
    public static final long DELIVERED = 4;
    public static final long CANCLE = 5;
    private Long id;
    private double totalPrice;

    private Date orderDate;
    private String status;

    @SerializedName("addressDTOResponse")
    private Address address;

    @SerializedName("cartDetailsDTOResponseList")
    private List<Cart> cartList;

    public Orders(){}

    public Orders(Long id, double totalPrice, Date orderDate, String status, Address address, List<Cart> cartList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.cartList = cartList;
    }

    public Long getId() {
        return id;
    }

    public String getDateFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(orderDate);

        return formattedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
