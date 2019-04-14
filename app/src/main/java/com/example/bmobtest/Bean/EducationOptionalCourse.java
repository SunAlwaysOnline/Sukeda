package com.example.bmobtest.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationOptionalCourse implements Serializable {
    private String time;
    private String title;
    private String url;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "EducationOptionalCourse{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
