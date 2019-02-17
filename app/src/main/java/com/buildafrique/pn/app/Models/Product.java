package com.buildafrique.pn.app.Models;

import com.buildafrique.pn.app.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Arrays;

/**
 * Created by ekirapa on 9/3/18 .
 */

public class Product extends BaseModel {

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String name;

    @Column
    private  String display_name;


    @Column
    private String categ_id = "false";

    @Column
    private String uom_id = "false";

    @Column
    private int standard_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getCateg_id() {
        return categ_id;
    }

    public void setCateg_id(String[] categ_id) {
        this.categ_id = Arrays.toString(categ_id);
    }

    public int getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(int standard_price) {
        this.standard_price = standard_price;
    }

    public String getUom_id() {
        return uom_id;
    }

    public void setUom_id(String[] uom_id) {
        this.uom_id = Arrays.toString(uom_id);
    }
}
