package com.huuduc.giuaky.data;

import java.io.Serializable;

public class Promotion implements Serializable {

    private Long id;
    private double discount;
    private boolean status;

    public Promotion() {
    }

    public Promotion(Long id, double discount, boolean status) {
        this.id = id;
        this.discount = discount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
