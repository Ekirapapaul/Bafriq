package com.buildafrique.pn.app.Models;

/**
 * Sales order model class
 */
public class SalesOrder {
    private String origin;
    private String[] message_follower_ids = {};
    private String create_date;
    private String[] categ_ids = {};
    private String[] order_line = {};
    private String picking_policy;
    private String invoiced_rate;
    private String campaign_id;
    private String carrier_id;
    private String[] write_uid = {};
    private String[] currency_id = {};
    private String[] invoice_ids = {};
    private boolean invoice_exists;
    private String client_order_ref;
    private String display_name;
    private String date_order;
    private String[] partner_id = {};
    private String[] message_ids ={};
    private boolean invoiced;
    private String amount_tax;
    private boolean procurement_group_id;
    private boolean fiscal_position;
    private String amount_untaxed;
    private  boolean message_is_follower;
    private String __last_update;
    private String[] payment_term = {};
    private String message_last_post;
    private String[] company_id = {};
    private String id;
    private String note;
    private String state;
    private String is_property;
    private String[] order_line2 = {};
    private String paypal_url;
    private String[] pricelist_id = {};
    private String message_summary;


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public String[] getCateg_ids() {
        return categ_ids;
    }

    public void setCateg_ids(String[] categ_ids) {
        this.categ_ids = categ_ids;
    }

    public String[] getOrder_line() {
        return order_line;
    }

    public void setOrder_line(String[] order_line) {
        this.order_line = order_line;
    }

    public String getPicking_policy() {
        return picking_policy;
    }

    public void setPicking_policy(String picking_policy) {
        this.picking_policy = picking_policy;
    }

    public String getInvoiced_rate() {
        return invoiced_rate;
    }

    public void setInvoiced_rate(String invoiced_rate) {
        this.invoiced_rate = invoiced_rate;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCarrier_id() {
        return carrier_id;
    }

    public void setCarrier_id(String carrier_id) {
        this.carrier_id = carrier_id;
    }

    public String[] getWrite_uid() {
        return write_uid;
    }

    public void setWrite_uid(String[] write_uid) {
        this.write_uid = write_uid;
    }

    public String[] getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String[] currency_id) {
        this.currency_id = currency_id;
    }

    public String[] getInvoice_ids() {
        return invoice_ids;
    }

    public void setInvoice_ids(String[] invoice_ids) {
        this.invoice_ids = invoice_ids;
    }

    public boolean isInvoice_exists() {
        return invoice_exists;
    }

    public void setInvoice_exists(boolean invoice_exists) {
        this.invoice_exists = invoice_exists;
    }

    public String getClient_order_ref() {
        return client_order_ref;
    }

    public void setClient_order_ref(String client_order_ref) {
        this.client_order_ref = client_order_ref;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDate_order() {
        return date_order;
    }

    public void setDate_order(String date_order) {
        this.date_order = date_order;
    }

    public String[] getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String[] partner_id) {
        this.partner_id = partner_id;
    }

    public String[] getMessage_ids() {
        return message_ids;
    }

    public void setMessage_ids(String[] message_ids) {
        this.message_ids = message_ids;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public String getAmount_tax() {
        return amount_tax;
    }

    public void setAmount_tax(String amount_tax) {
        this.amount_tax = amount_tax;
    }

    public boolean isProcurement_group_id() {
        return procurement_group_id;
    }

    public void setProcurement_group_id(boolean procurement_group_id) {
        this.procurement_group_id = procurement_group_id;
    }

    public boolean isFiscal_position() {
        return fiscal_position;
    }

    public void setFiscal_position(boolean fiscal_position) {
        this.fiscal_position = fiscal_position;
    }

    public String getAmount_untaxed() {
        return amount_untaxed;
    }

    public void setAmount_untaxed(String amount_untaxed) {
        this.amount_untaxed = amount_untaxed;
    }

    public boolean isMessage_is_follower() {
        return message_is_follower;
    }

    public void setMessage_is_follower(boolean message_is_follower) {
        this.message_is_follower = message_is_follower;
    }

    public String get__last_update() {
        return __last_update;
    }

    public void set__last_update(String __last_update) {
        this.__last_update = __last_update;
    }

    public String[] getPayment_term() {
        return payment_term;
    }

    public void setPayment_term(String[] payment_term) {
        this.payment_term = payment_term;
    }

    public String getMessage_last_post() {
        return message_last_post;
    }

    public void setMessage_last_post(String message_last_post) {
        this.message_last_post = message_last_post;
    }

    public String[] getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String[] company_id) {
        this.company_id = company_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIs_property() {
        return is_property;
    }

    public void setIs_property(String is_property) {
        this.is_property = is_property;
    }

    public String[] getOrder_line2() {
        return order_line2;
    }

    public void setOrder_line2(String[] order_line2) {
        this.order_line2 = order_line2;
    }

    public String getPaypal_url() {
        return paypal_url;
    }

    public void setPaypal_url(String paypal_url) {
        this.paypal_url = paypal_url;
    }

    public String[] getPricelist_id() {
        return pricelist_id;
    }

    public void setPricelist_id(String[] pricelist_id) {
        this.pricelist_id = pricelist_id;
    }

    public String getMessage_summary() {
        return message_summary;
    }

    public void setMessage_summary(String message_summary) {
        this.message_summary = message_summary;
    }
}
