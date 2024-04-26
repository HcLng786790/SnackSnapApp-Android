package com.huuduc.giuaky.data.DTO;

public class AuthenDTOResponse {
    private String msg;
    private String token;
    private Long userId;

    private Long roleId;

    private boolean active;



    public AuthenDTOResponse(){}

    public AuthenDTOResponse(String msg, String token, Long userId,Long roleId,boolean active) {
        this.msg = msg;
        this.token = token;
        this.userId = userId;
        this.active=active;
        this.roleId=roleId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
