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
@Table(database = AppDatabase.class, name = "conversionRate")
public class ConversionRate extends BaseModel implements Parcelable {

    public ConversionRate(){}

    @PrimaryKey(autoincrement = true)
    @Column
    private long id;

    @Column
    private String bonus_to_loyalty;

    @Column
    private String commission_to_loyalty;

    protected ConversionRate(Parcel in) {
        id = in.readLong();
        bonus_to_loyalty = in.readString();
        commission_to_loyalty = in.readString();
    }

    public static final Creator<ConversionRate> CREATOR = new Creator<ConversionRate>() {
        @Override
        public ConversionRate createFromParcel(Parcel in) {
            return new ConversionRate(in);
        }

        @Override
        public ConversionRate[] newArray(int size) {
            return new ConversionRate[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBonus_to_loyalty() {
        return bonus_to_loyalty;
    }

    public void setBonus_to_loyalty(String bonus_to_loyalty) {
        this.bonus_to_loyalty = bonus_to_loyalty;
    }

    public String getCommission_to_loyalty() {
        return commission_to_loyalty;
    }

    public void setCommission_to_loyalty(String commission_to_loyalty) {
        this.commission_to_loyalty = commission_to_loyalty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(bonus_to_loyalty);
        parcel.writeString(commission_to_loyalty);
    }
}
