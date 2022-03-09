package com.gohool.restaurant.bestrestaurant.Model;

import java.util.List;

/**
 * Created by Akwasi on 3/27/2018.
 */

public class Request {
    private String orderNo;
    private String tableNo, customerPhone, waiterEmail, total;
    private String status;
    private List<OrderItem> foodsRequested;

    public Request() {
    }

    public Request(String waiterEmail, String tableNo, String customerPhone, String total, List<OrderItem> foodsRequested) {
        this.tableNo = tableNo;
        this.customerPhone = customerPhone;
        this.total = total;
        this.foodsRequested = foodsRequested;
        this.waiterEmail = waiterEmail;
        this.status = "0"; //Default(0): In Queue; 1: Processing; 2: Done; 3: Cancelled
    }

    public Request(String waiterEmail, String tableNo, String total, List<OrderItem> foodsRequested) {
        this.tableNo = tableNo;
        this.total = total;
        this.foodsRequested = foodsRequested;
        this.waiterEmail = waiterEmail;
    }

    public Request(String orderNo, String tableNo, String customerPhone, String status) {
        this.orderNo = orderNo;
        this.tableNo = tableNo;
        this.customerPhone = customerPhone;
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaiterEmail() {
        return waiterEmail;
    }

    public void setWaiterEmail(String waiterEmail) {
        this.waiterEmail = waiterEmail;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderItem> getFoodsRequested() {
        return foodsRequested;
    }

    public void setFoodsRequested(List<OrderItem> foodsRequested) {
        this.foodsRequested = foodsRequested;
    }
}
