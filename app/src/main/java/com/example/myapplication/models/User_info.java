package com.example.myapplication.models;

import java.util.List;

public class User_info {

    private String uId, displayName, createDate, phone, email, avatarLink;
    private List<String>CCCDImg;
    private boolean status;

    public User_info() {
    }

    public User_info(String uId, String displayName, String createDate, String phone, String email, String avatarLink, List<String> CCCDImg, boolean status) {
        this.uId = uId;
        this.displayName = displayName;
        this.createDate = createDate;
        this.phone = phone;
        this.email = email;
        this.avatarLink = avatarLink;
        this.CCCDImg = CCCDImg;
        this.status = status;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public List<String> getCCCDImg() {
        return CCCDImg;
    }

    public void setCCCDImg(List<String> CCCDImg) {
        this.CCCDImg = CCCDImg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
