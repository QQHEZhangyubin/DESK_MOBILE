package com.example.desk.entity;

import java.util.ArrayList;

public class ShuoShuo {

    /**
     * 说说的实体类
     * picture : http://localhost/images/oneimg
     * time : null
     * userName : 2016021052
     * content : 2019年1月28日
     * browser : 12
     * epicture : ["http://localhost/images/oneimg"]
     */

    private String picture;
    private String time;
    private String userName;
    private String content;
    private String browser;
    private ArrayList<String> epicture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public ArrayList<String> getEpicture() {
        return epicture;
    }

    public void setEpicture(ArrayList<String> epicture) {
        this.epicture = epicture;
    }
}
