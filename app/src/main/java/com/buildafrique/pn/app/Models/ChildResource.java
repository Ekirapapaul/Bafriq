package com.buildafrique.pn.app.Models;

/**
 * Created by ekirapa on 10/21/18 .
 */
public class ChildResource {
    private String title;
    private int icon;

    public ChildResource(String title, int icon) {
        setIcon(icon);
        setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
