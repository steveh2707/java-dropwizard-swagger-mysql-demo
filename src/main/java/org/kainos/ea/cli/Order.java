package org.kainos.ea.cli;

import java.sql.*;

public class Order implements Comparable<Order> {
    private int orderId;
    private  int customerId;
    private Timestamp orderDate;
    private Date dispatchDate;
    private Date arrivedDate;

    public Order(int orderId, int customerId, Timestamp orderDate, Date dispatchDate, Date arrivedDate) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setDispatchDate(dispatchDate);
        this.setArrivedDate(arrivedDate);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    @Override
    public int compareTo(Order o) {
        return this.orderDate.compareTo(o.getOrderDate());
    }

    @Override
    public String toString() {
        return "Order ID: " + this.getOrderId() + ", Customer ID: " + this.getCustomerId() + ", Order Date: " + this.getOrderDate();
    }

}
