package com.example.bmobtest;

import junit.framework.Assert;

/**
 * Created by 戚春阳 on 2018/5/14.
 */

public class CalculaterTest {
    Calculater calculater=new Calculater();
    @org.junit.Test
    public void addTest(){
        int a=1;
        int b=2;
        int result=calculater.add(a,b);
        Assert.assertEquals(result,3);
    }
}
