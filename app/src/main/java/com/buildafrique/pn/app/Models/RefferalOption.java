package com.buildafrique.pn.app.Models;

import android.support.v4.content.PermissionChecker;

import com.buildafrique.pn.app.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 12/6/18 .
 */
@Table(database = AppDatabase.class, name = "refferalOption")
public class RefferalOption extends BaseModel {
    @Column
    @PrimaryKey
    private int product_id;

    @Column
    private int loyalty_amount;

    @Column
    private String name;


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getLoyalty_amount() {
        return loyalty_amount;
    }

    public void setLoyalty_amount(int loyalty_amount) {
        this.loyalty_amount = loyalty_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
