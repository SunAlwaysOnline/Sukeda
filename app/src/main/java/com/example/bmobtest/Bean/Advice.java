package com.example.bmobtest.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 戚春阳 on 2018/2/11.
 */

public class Advice extends BmobObject {
    private User user;
    private String title;
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
