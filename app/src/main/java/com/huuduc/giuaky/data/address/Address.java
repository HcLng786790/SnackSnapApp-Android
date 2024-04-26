package com.huuduc.giuaky.data.address;

import java.io.Serializable;

public class Address implements Serializable {
    private Long id;
    private String location;
    private String nameReceiver;
    private String phoneReceiver;
    private boolean defaults;

    public Address(){}

    public Address(Long id, String location, String nameReceiver, String phoneReceiver, boolean defaults) {
        this.id = id;
        this.location = location;
        this.nameReceiver = nameReceiver;
        this.phoneReceiver = phoneReceiver;
        this.defaults = defaults;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public boolean isDefaults() {
        return defaults;
    }

    public void setDefaults(boolean defaults) {
        this.defaults = defaults;
    }
}
