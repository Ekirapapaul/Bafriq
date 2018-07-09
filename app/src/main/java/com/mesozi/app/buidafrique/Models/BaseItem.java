package com.mesozi.app.buidafrique.Models;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class BaseItem {
    private String id;
    private String title;
    private String period;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
