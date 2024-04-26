package com.huuduc.giuaky.data.DTO;

public class ChangePassDTO {

    private String newPassword;
    private String oldPassword;

    public ChangePassDTO(){
    }

    public ChangePassDTO(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
