package com.mesozi.app.buidafrique.Models;

/**
 * Created by ekirapa on 7/26/18 .
 */
public class OrderLine {
    private int price_unit;
    private int product_uom_qty;
    private int price_subtotal;
    private String name;

    public int getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(int price_unit) {
        this.price_unit = price_unit;
    }

    public int getProduct_uom_qty() {
        return product_uom_qty;
    }

    public void setProduct_uom_qty(int product_uom_qty) {
        this.product_uom_qty = product_uom_qty;
    }

    public int getPrice_subtotal() {
        return price_subtotal;
    }

    public void setPrice_subtotal(int price_subtotal) {
        this.price_subtotal = price_subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
