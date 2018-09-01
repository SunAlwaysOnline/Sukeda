package com.example.bmobtest.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 戚春阳 on 2018/2/24.
 */

public class EducationOptionalCourse implements Serializable {
    private String name;
    private String xueNian_xueQi;
    private String category;
    private String limit;
    private String course_time;
    private String teacher;
    private String department;
    private String issure_time;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXueNian_xueQi() {
        return xueNian_xueQi;
    }

    public void setXueNian_xueQi(String xueNian_xueQi) {
        this.xueNian_xueQi = xueNian_xueQi;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getCourse_time() {
        return course_time;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIssure_time() {
        return issure_time;
    }

    public void setIssure_time(String issure_time) {
        this.issure_time = issure_time;
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
                "name='" + name + '\'' +
                ", xueNian_xueQi='" + xueNian_xueQi + '\'' +
                ", category='" + category + '\'' +
                ", limit='" + limit + '\'' +
                ", course_time='" + course_time + '\'' +
                ", teacher='" + teacher + '\'' +
                ", department='" + department + '\'' +
                ", issure_time='" + issure_time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
