package com.example.bmobtest.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 戚春阳 on 2018/2/1.
 * 词条分类
 */

public class EntryCategory extends BmobObject {
    private String name;
    private Integer number;

    public String getName() {
        return name;
    }

    public void setName(String category) {
        this.name = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
