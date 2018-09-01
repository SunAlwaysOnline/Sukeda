package com.example.bmobtest.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 戚春阳 on 2018/1/31.
 */

public class FunctionState extends BmobObject{
    private String name;
    private Boolean isOpen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
