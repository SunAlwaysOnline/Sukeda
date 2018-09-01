package com.example.bmobtest.Bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 戚春阳 on 2018/2/23.
 */

public class Version extends BmobObject {
    //版本号
    private Integer version;
    //版本简述
    private String description;
    //发布时间
    private BmobDate release;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobDate getRelease() {
        return release;
    }

    public void setRelease(BmobDate release) {
        this.release = release;
    }
}
