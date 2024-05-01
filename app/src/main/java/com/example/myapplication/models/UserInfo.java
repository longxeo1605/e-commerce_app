package com.example.myapplication.models;

import java.util.List;

public class UserInfo {

    private String uid, displayName, createDate, cccd, phone, email, avatarLink, roleId;
    private List<String>CCCDImg;
    private boolean status;

    public UserInfo() {
    }

    public UserInfo(String uid, String displayName, String createDate, String cccd, String phone, String email, String avatarLink, String roleId, List<String> CCCDImg, boolean status) {
        this.uid = uid;
        this.displayName = displayName;
        this.createDate = createDate;
        this.cccd = cccd;
        this.phone = phone;
        this.email = email;
        this.avatarLink = avatarLink;
        this.roleId = roleId;
        this.CCCDImg = CCCDImg;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
