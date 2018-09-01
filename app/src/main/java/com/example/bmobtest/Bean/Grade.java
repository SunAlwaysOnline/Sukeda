package com.example.bmobtest.Bean;

/**
 * 考试成绩的bean
 * <p>
 * Created by 戚春阳 on 2017/12/29.
 */

public class Grade {
    private String xn;
    private String xq;
    private String course_code;
    private String course_name;
    private String course_nature;
    private String course_belong;
    private String credit;
    private String gpa;
    private String mark;
    private String minor_mark;
    private String score_make_up;
    private String score_rebuild;
    private String college;
    private String remark;
    private String mark_rebuild;

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_nature() {
        return course_nature;
    }

    public void setCourse_nature(String course_nature) {
        this.course_nature = course_nature;
    }

    public String getCourse_belong() {
        return course_belong;
    }

    public void setCourse_belong(String course_belong) {
        this.course_belong = course_belong;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMinor_mark() {
        return minor_mark;
    }

    public void setMinor_mark(String minor_mark) {
        this.minor_mark = minor_mark;
    }

    public String getScore_make_up() {
        return score_make_up;
    }

    public void setScore_make_up(String score_make_up) {
        this.score_make_up = score_make_up;
    }

    public String getScore_rebuild() {
        return score_rebuild;
    }

    public void setScore_rebuild(String score_rebuild) {
        this.score_rebuild = score_rebuild;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMark_rebuild() {
        return mark_rebuild;
    }

    public void setMark_rebuild(String mark_rebuild) {
        this.mark_rebuild = mark_rebuild;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "xn='" + xn + '\'' +
                ", xq='" + xq + '\'' +
                ", course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", course_nature='" + course_nature + '\'' +
                ", course_belong='" + course_belong + '\'' +
                ", credit='" + credit + '\'' +
                ", gpa='" + gpa + '\'' +
                ", mark='" + mark + '\'' +
                ", minor_mark='" + minor_mark + '\'' +
                ", score_make_up='" + score_make_up + '\'' +
                ", score_rebuild='" + score_rebuild + '\'' +
                ", college='" + college + '\'' +
                ", remark='" + remark + '\'' +
                ", mark_rebuild='" + mark_rebuild + '\'' +
                '}';
    }
}
