package com.buildafrique.pn.app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.buildafrique.pn.app.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 10/9/18 .
 */
@Table(database = AppDatabase.class, name = "loyalty")
public class Loyalty extends BaseModel implements Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column
    private long id;

    @Column
    private int available;

    @Column
    private int expected;

    @Column
    private int paid;

    @Column
    private int redeemed;

    public Loyalty() {
    }

    protected Loyalty(Parcel in) {
        id = in.readLong();
        available = in.readInt();
        expected = in.readInt();
        paid = in.readInt();
        redeemed = in.readInt();
    }

    public static final Creator<Loyalty> CREATOR = new Creator<Loyalty>() {
        @Override
        public Loyalty createFromParcel(Parcel in) {
            return new Loyalty(in);
        }

        @Override
        public Loyalty[] newArray(int size) {
            return new Loyalty[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(int redeemed) {
        this.redeemed = redeemed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(available);
        parcel.writeInt(expected);
        parcel.writeInt(paid);
        parcel.writeInt(redeemed);
    }
}
