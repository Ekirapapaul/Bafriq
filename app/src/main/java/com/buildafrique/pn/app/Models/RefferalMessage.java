package com.buildafrique.pn.app.Models;

import com.buildafrique.pn.app.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 12/9/18 .
 */
@Table(database = AppDatabase.class, name = "refferalmessage")
public class RefferalMessage extends BaseModel {
    @PrimaryKey
    @Column
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
