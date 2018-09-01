package com.example.bmobtest.Bean;

/**
 * 课外学分的实体类
 * Created by 戚春阳 on 2017/12/17.
 */

public class Kwxf {
    private String time;
    private String name;
    private double grade;
    private String pass;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Kwxf{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", pass='" + pass + '\'' +
                '}';
    }
}
