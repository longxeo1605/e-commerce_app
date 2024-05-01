package com.example.myapplication.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class DSDetail {
    private String statusId;
    private String orderId;
    private Date dateofStatus;

    public DSDetail(String statusId, String orderId, Date dateofStatus) {
        this.statusId = statusId;
        this.orderId = orderId;
        this.dateofStatus = dateofStatus;
    }

    public DSDetail() {
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDateOfStatus() {
        return dateofStatus;
    }

    public void setDateOfStatus(Date dateOfStatus) {
        this.dateofStatus = dateOfStatus;
    }

    @Override
    public String toString() {
        return "DSDetail{" +
                "statusId='" + statusId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", dateofStatus=" + dateofStatus +
                '}';
    }
}
