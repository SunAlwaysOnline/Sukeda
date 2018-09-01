package com.example.bmobtest.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 戚春阳 on 2017/12/12.
 */

public class Game2048Rank extends BmobObject {
    private String account;
    private Integer score;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
