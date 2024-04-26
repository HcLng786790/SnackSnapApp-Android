package com.huuduc.giuaky.data.DTO;

public class RegisterDTORequest {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;



    public RegisterDTORequest(){}

    public RegisterDTORequest(String email, String password,String phoneNumber,String name) {
        this.email = email;
        this.password = password;
        this.phoneNumber=phoneNumber;
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
