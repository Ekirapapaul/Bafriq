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
    @PrimaryKey
    @Column
    private int id;

    @Column
    private String city;

    @Column
    private String name;

    @Column
    private String zip;

    @Column
    private String phone ;

    @Column
    private String street;

    @Column
    private String email;


    protected Customer(Parcel in) {
        id = in.readInt();
        city = in.readString();
        name = in.readString();
        zip = in.readString();
        phone = in.readString();
        street = in.readString();
        email = in.readString();
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(city);
        parcel.writeString(name);
        parcel.writeString(zip);
        parcel.writeString(phone);
        parcel.writeString(street);
        parcel.writeString(email);
    }
}
