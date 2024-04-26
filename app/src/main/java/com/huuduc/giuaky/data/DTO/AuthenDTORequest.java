package com.huuduc.giuaky.data.DTO;

public class AuthenDTORequest {

    private String email;
    private String password;

    public AuthenDTORequest(){}

    public AuthenDTORequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
