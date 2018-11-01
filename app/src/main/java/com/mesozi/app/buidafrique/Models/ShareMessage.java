package com.mesozi.app.buidafrique.Models;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 10/29/18 .
 */
@Table(database = AppDatabase.class, name = "share_message")
public class ShareMessage extends BaseModel {

    public ShareMessage() {
    }

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String message;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
