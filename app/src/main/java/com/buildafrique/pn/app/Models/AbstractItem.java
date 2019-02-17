package com.buildafrique.pn.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class AbstractItem {
    private boolean furnished;
    private String[] write_uid;
    private String[] categ_ids;
    private int day_close;
    private int day_open;
    private String property_id;
    private String message_summary = "";
    private String display_name;
    private String opt_out;
    private String title;
    private String type_id;
    private String[] company_id = {"false"};
    private String max_bedroom;
    private String parent_id;
    private String min_price;
    private String fax;
    private String[] child_ids= {"false"};
    private String partner_address_email;
    private String name = "";
    private String facing;
    private String medium_id;
    private String referred;
    private String message_bounce;
    private String email_send;
    private String[] message_follower_ids = {"false"};
    private String create_date;
    private String date_last_stage_update;
    private String campaign_id;
    private String partner_address_name;
    private String contact_name;
    private String[] partner_id = {"false"};
    private List<Object> country_id = new ArrayList<>();
    private  String description = "";

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public String[] getWrite_uid() {
        return write_uid;
    }

    public void setWrite_uid(String[] write_uid) {
        this.write_uid = write_uid;
    }

    public String[] getCateg_ids() {
        return categ_ids;
    }

    public void setCateg_ids(String[] categ_ids) {
        this.categ_ids = categ_ids;
    }

    public int getDay_close() {
        return day_close;
    }

    public void setDay_close(int day_close) {
        this.day_close = day_close;
    }

    public int getDay_open() {
        return day_open;
    }

    public void setDay_open(int day_open) {
        this.day_open = day_open;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getMessage_summary() {
        return message_summary;
    }

    public void setMessage_summary(String message_summary) {
        this.message_summary = message_summary;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getOpt_out() {
        return opt_out;
    }

    public void setOpt_out(String opt_out) {
        this.opt_out = opt_out;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String[] getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String[] company_id) {
        this.company_id = company_id;
    }

    public String getMax_bedroom() {
        return max_bedroom;
    }

    public void setMax_bedroom(String max_bedroom) {
        this.max_bedroom = max_bedroom;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String[] getChild_ids() {
        return child_ids;
    }

    public void setChild_ids(String[] child_ids) {
        this.child_ids = child_ids;
    }

    public String getPartner_address_email() {
        return partner_address_email;
    }

    public void setPartner_address_email(String partner_address_email) {
        this.partner_address_email = partner_address_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getMedium_id() {
        return medium_id;
    }

    public void setMedium_id(String medium_id) {
        this.medium_id = medium_id;
    }

    public String getReferred() {
        return referred;
    }

    public void setReferred(String referred) {
        this.referred = referred;
    }

    public String getMessage_bounce() {
        return message_bounce;
    }

    public void setMessage_bounce(String message_bounce) {
        this.message_bounce = message_bounce;
    }

    public String getEmail_send() {
        return email_send;
    }

    public void setEmail_send(String email_send) {
        this.email_send = email_send;
    }

    public String[] getMessage_follower_ids() {
        return message_follower_ids;
    }

    public void setMessage_follower_ids(String[] message_follower_ids) {
        this.message_follower_ids = message_follower_ids;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDate_last_stage_update() {
        return date_last_stage_update;
    }

    public void setDate_last_stage_update(String date_last_stage_update) {
        this.date_last_stage_update = date_last_stage_update;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getPartner_address_name() {
        return partner_address_name;
    }

    public void setPartner_address_name(String partner_address_name) {
        this.partner_address_name = partner_address_name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String[] getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String[] partner_id) {
        this.partner_id = partner_id;
    }
//
//    public List<Object> getCountry_id() {
//        return country_id;
//    }
//
//    public void setCountry_id(List<Object> country_id) {
//        this.country_id = country_id;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
