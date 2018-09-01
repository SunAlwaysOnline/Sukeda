package com.example.bmobtest.Bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * User基本表
 * Created by 戚春阳 on 2017/12/8.
 */

public class User extends BmobUser {
    //昵称
    private String nick;
    //性别
    private String sex;
    //QQ号
    private String qq;
    //微信号
    private String weChat;
    //年龄
    private Integer age;
    //生日
    private Date birthday;
    //宿舍号
    private String dorm;
    //姓名
    private String st_name;
    //教务系统账号
    private String st_id;
    //教务系统密码
    private String st_pd;
    //身份证号
    private String st_identity;
    //年级
    private Integer grade;
    //学院
    private String academy;
    //专业
    private String major;
    //班级
    private String classroom;
    //家庭住址
    private String address;
    //头像文件的地址
    private BmobFile file;
    //个人描述
    private String description;
    //收获的赞总数
    private Integer stars;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDorm() {
        return dorm;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getSt_pd() {
        return st_pd;
    }

    public void setSt_pd(String st_pd) {
        this.st_pd = st_pd;
    }

    public String getSt_identity() {
        return st_identity;
    }

    public void setSt_identity(String st_identity) {
        this.st_identity = st_identity;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
