package com.example.bmobtest.Bean;


import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 签到
 * Created by 戚春阳 on 2017/12/9.
 */

public class Sign extends BmobObject {
    private String account;
    private Date signDate;
    private String generalsignDate;

    public Sign(String account, Date signDate, String generalsignDate) {
        this.account = account;
        this.signDate = signDate;
        this.generalsignDate = generalsignDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getGeneralsignDate() {
        return generalsignDate;
    }

    public void setGeneralsignDate(String generalsignDate) {
        this.generalsignDate = generalsignDate;
    }
}
