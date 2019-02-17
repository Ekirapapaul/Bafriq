package com.buildafrique.pn.app.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ekirapa on 7/26/18 .
 */
public class OrderLine implements Parcelable {
    private int price_unit;
    private int product_uom_qty;
    private int price_subtotal;
    private String name;

    protected OrderLine(Parcel in) {
        price_unit = in.readInt();
        product_uom_qty = in.readInt();
        price_subtotal = in.readInt();
        name = in.readString();
    }

    public static final Creator<OrderLine> CREATOR = new Creator<OrderLine>() {
        @Override
        public OrderLine createFromParcel(Parcel in) {
            return new OrderLine(in);
        }

        @Override
        public OrderLine[] newArray(int size) {
            return new OrderLine[size];
        }
    };

    public int getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(int price_unit) {
        this.price_unit = price_unit;
    }

    public int getProduct_uom_qty() {
        return product_uom_qty;
    }

    public void setProduct_uom_qty(int product_uom_qty) {
        this.product_uom_qty = product_uom_qty;
    }

    public int getPrice_subtotal() {
        return price_subtotal;
    }

    public void setPrice_subtotal(int price_subtotal) {
        this.price_subtotal = price_subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(price_unit);
        parcel.writeInt(product_uom_qty);
        parcel.writeInt(price_subtotal);
        parcel.writeString(name);
    }
}
