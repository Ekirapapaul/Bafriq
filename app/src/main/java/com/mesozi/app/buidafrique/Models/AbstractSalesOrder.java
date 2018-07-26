package com.mesozi.app.buidafrique.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

/**
 * Abstract sales orders model
 */
public class AbstractSalesOrder implements Parcelable{
    private String state;
    private String user_id;
    private String name;
    private String client_order_ref;
    private List<OrderLine> order_line;
    private String date;
    private String partner_id;
    private String id;
    private String amount_total;


    protected AbstractSalesOrder(Parcel in) {
        state = in.readString();
        user_id = in.readString();
        name = in.readString();
        client_order_ref = in.readString();
        order_line = in.createTypedArrayList(OrderLine.CREATOR);
        date = in.readString();
        partner_id = in.readString();
        id = in.readString();
        amount_total = in.readString();
    }

    public static final Creator<AbstractSalesOrder> CREATOR = new Creator<AbstractSalesOrder>() {
        @Override
        public AbstractSalesOrder createFromParcel(Parcel in) {
            return new AbstractSalesOrder(in);
        }

        @Override
        public AbstractSalesOrder[] newArray(int size) {
            return new AbstractSalesOrder[size];
        }
    };

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient_order_ref() {
        return client_order_ref;
    }

    public void setClient_order_ref(String client_order_ref) {
        this.client_order_ref = client_order_ref;
    }

    public List<OrderLine> getOrder_line() {
        return order_line;
    }

    public void setOrder_line(List<OrderLine> order_line) {
        this.order_line = order_line;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount_total() {
        return amount_total;
    }

    public void setAmount_total(String amount_total) {
        this.amount_total = amount_total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(state);
        parcel.writeString(user_id);
        parcel.writeString(name);
        parcel.writeString(client_order_ref);
        parcel.writeTypedList(order_line);
        parcel.writeString(date);
        parcel.writeString(partner_id);
        parcel.writeString(id);
        parcel.writeString(amount_total);
    }
}
