package com.huuduc.giuaky.data.cart;

import java.io.Serializable;
import java.util.List;

public class CartWr implements Serializable {

    private Long id;
    private List<Cart> cartDetailsDTOResponseList;

    private double totalPrice;

    public CartWr(){}

    public CartWr(Long id, List<Cart> cartDetailsDTOResponseList, double totalPrice) {
        this.id = id;
        this.cartDetailsDTOResponseList = cartDetailsDTOResponseList;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cart> getCartDetailsDTOResponseList() {
        return cartDetailsDTOResponseList;
    }

    public void setCartDetailsDTOResponseList(List<Cart> cartDetailsDTOResponseList) {
        this.cartDetailsDTOResponseList = cartDetailsDTOResponseList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
