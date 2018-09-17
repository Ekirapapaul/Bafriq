package com.mesozi.app.buidafrique.Models;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ekirapa on 9/3/18 .
 */
@Table(database = AppDatabase.class, name = "custom_products")

public class ProductCategory extends BaseModel {

    public ProductCategory(String name, String url, int categoryId) {
        setCategoryId(categoryId);
        setName(name);
        setUrl(url);
    }

    public ProductCategory() {

    }

    @Column
    @PrimaryKey
    private String name;

    @Column
    private String url;

    @Column
    private int categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
