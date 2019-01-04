package com.mesozi.app.buidafrique.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 9/5/18 .
 */

@Table(database = AppDatabase.class, name = "email_message")
public class EmailMessage extends BaseModel implements Parcelable {

    public EmailMessage() {
    }

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String display_name;

    @Column
    private String email_from;

    @Column
    private String date;

    @Column
    private boolean starred;

    @Column
    private String description;

    @Column
    private String subject = "false";

    @Column
    private String body;

    @Column
    private int user_id;

    @Column
    private String author_id;

    @Column
    private String create_date;

    @Column
    private String record_name;

    @Column
    private boolean to_read;

    @Column
    private String parent_id;

    @Column
    private String subtype_id;

    @Column
    private String write_date;

    @Column
    private String type;

    @Column
    private String model;

    @Column
    private int res_id;

    protected EmailMessage(Parcel in) {
        id = in.readString();
        display_name = in.readString();
        email_from = in.readString();
        date = in.readString();
        starred = in.readByte() != 0;
        description = in.readString();
        subject = in.readString();
        body = in.readString();
        user_id = in.readInt();
        author_id = in.readString();
        create_date = in.readString();
        record_name = in.readString();
        to_read = in.readByte() != 0;
        parent_id = in.readString();
        subtype_id = in.readString();
        write_date = in.readString();
        type = in.readString();
        model = in.readString();
        res_id = in.readInt();
    }

    public static final Creator<EmailMessage> CREATOR = new Creator<EmailMessage>() {
        @Override
        public EmailMessage createFromParcel(Parcel in) {
            return new EmailMessage(in);
        }

        @Override
        public EmailMessage[] newArray(int size) {
            return new EmailMessage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail_from() {
        return email_from;
    }

    public void setEmail_from(String email_from) {
        this.email_from = email_from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getRecord_name() {
        return record_name;
    }

    public void setRecord_name(String record_name) {
        this.record_name = record_name;
    }

    public boolean isTo_read() {
        return to_read;
    }

    public void setTo_read(boolean to_read) {
        this.to_read = to_read;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSubtype_id() {
        return subtype_id;
    }

    public void setSubtype_id(String subtype_id) {
        this.subtype_id = subtype_id;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(display_name);
        parcel.writeString(email_from);
        parcel.writeString(date);
        parcel.writeByte((byte) (starred ? 1 : 0));
        parcel.writeString(description);
        parcel.writeString(subject);
        parcel.writeString(body);
        parcel.writeInt(user_id);
        parcel.writeString(author_id);
        parcel.writeString(create_date);
        parcel.writeString(record_name);
        parcel.writeByte((byte) (to_read ? 1 : 0));
        parcel.writeString(parent_id);
        parcel.writeString(subtype_id);
        parcel.writeString(write_date);
        parcel.writeString(type);
        parcel.writeString(model);
        parcel.writeInt(res_id);
    }
}
