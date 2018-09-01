package com.example.bmobtest.Bean;

import java.io.Serializable;

/** 校园新闻
 * Created by 戚春阳 on 2017/12/13.
 */

public class News implements Serializable {
    private String department;
    private String title;
    private String url;
    private String read;
    private String date;
    private Boolean isRed;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getRed() {
        return isRed;
    }

    public void setRed(Boolean red) {
        isRed = red;
    }

    @Override
    public String toString() {
        return "News{" +
                "department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", read='" + read + '\'' +
                ", date='" + date + '\'' +
                ", isRed=" + isRed +
                '}';
    }
}
