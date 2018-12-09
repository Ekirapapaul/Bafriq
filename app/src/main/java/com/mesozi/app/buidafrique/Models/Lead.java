package com.mesozi.app.buidafrique.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

//Leads object model
@Table(database = AppDatabase.class, name = "lead")
public class Lead extends BaseModel implements  Parcelable{

    public Lead() {
    }

    @Column
    @PrimaryKey
    private int id;
    @Column
    private String create_date;

    @Column
    private String description;

    @Column
    private String contact_name;

    @Column
    private String planned_revenue;

    @Column
    private String stage_id;

    @Column
    private int user_id;

    @Column
    private String name;

    @Column
    private String partner_name;

    @ColumnIgnore
    @Column
    private String partner_id;

    @Column
    private String mobile;

    @Column
    private String type;


    protected Lead(Parcel in) {
        id = in.readInt();
        create_date = in.readString();
        description = in.readString();
        contact_name = in.readString();
        planned_revenue = in.readString();
        stage_id = in.readString();
        user_id = in.readInt();
        name = in.readString();
        partner_name = in.readString();
        partner_id = in.readString();
        mobile = in.readString();
        type = in.readString();
    }

    public static final Creator<Lead> CREATOR = new Creator<Lead>() {
        @Override
        public Lead createFromParcel(Parcel in) {
            return new Lead(in);
        }

        @Override
        public Lead[] newArray(int size) {
            return new Lead[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(create_date);
        parcel.writeString(description);
        parcel.writeString(contact_name);
        parcel.writeString(planned_revenue);
        parcel.writeString(stage_id);
        parcel.writeInt(user_id);
        parcel.writeString(name);
        parcel.writeString(partner_name);
        parcel.writeString(partner_id);
        parcel.writeString(mobile);
        parcel.writeString(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPlanned_revenue() {
        return planned_revenue;
    }

    public void setPlanned_revenue(String planned_revenue) {
        this.planned_revenue = planned_revenue;
    }

    public String getStage_id() {
        return stage_id;
    }

    public void setStage_id(String stage_id) {
        this.stage_id = stage_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Creator<Lead> getCREATOR() {
        return CREATOR;
    }
}
