package com.mesozi.app.buidafrique.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Customers model class
 * Doubles up as the customers table.
 */

@Table(database = AppDatabase.class, name = "customer")
public class Customer extends BaseModel implements Parcelable {

    public Customer() {
    }
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String partner_name;

    private String[] partner_id = {};

    @Column
    private String partner_address_name;

    @Column
    private String partner_address_email;

    @Column
    private String mobile;

    protected Customer(Parcel in) {
        id = in.readInt();
        partner_name = in.readString();
        partner_id = in.createStringArray();
        partner_address_name = in.readString();
        partner_address_email = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String[] getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String[] partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_address_name() {
        return partner_address_name;
    }

    public void setPartner_address_name(String partner_address_name) {
        this.partner_address_name = partner_address_name;
    }

    public String getPartner_address_email() {
        return partner_address_email;
    }

    public void setPartner_address_email(String partner_address_email) {
        this.partner_address_email = partner_address_email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(partner_name);
        parcel.writeStringArray(partner_id);
        parcel.writeString(partner_address_name);
        parcel.writeString(partner_address_email);
        parcel.writeString(mobile);
    }

}
