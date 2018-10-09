package com.mesozi.app.buidafrique.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 10/9/18 .
 */
@Table(database = AppDatabase.class, name = "bonus")
public class Bonus extends BaseModel implements Parcelable {
    @PrimaryKey(autoincrement = true)
    @Column
    private long id;

    @Column
    private int available;

    @Column
    private int expected;

    @Column
    private int paid;

    public Bonus() {
    }

    protected Bonus(Parcel in) {
        id = in.readLong();
        available = in.readInt();
        expected = in.readInt();
        paid = in.readInt();
    }

    public static final Creator<Bonus> CREATOR = new Creator<Bonus>() {
        @Override
        public Bonus createFromParcel(Parcel in) {
            return new Bonus(in);
        }

        @Override
        public Bonus[] newArray(int size) {
            return new Bonus[size];
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
    }
}
