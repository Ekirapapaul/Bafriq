package com.buildafrique.pn.app.Models;

import com.buildafrique.pn.app.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 10/17/18 .
 */
@Table(database = AppDatabase.class, name = "promomessage")
public class PromoMessage extends BaseModel {
    public PromoMessage() {
    }

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String message;

    @Column
    private int sequence;


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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
