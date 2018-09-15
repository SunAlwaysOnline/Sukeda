package com.example.bmobtest.Constant;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class AppValue {
    //Application ID，SDK初始化必须用到此密钥
    public static final String APPLICATION_ID = "569666aea70f9de42be63242d8670854";
    //REST API Key，REST API请求中HTTP头部信息必须附带密钥之一
    public static final String REST_API_KEY = "803d4d7c4338027169f1cee664a4dbfc";
    //Secret Key，是SDK安全密钥，在云函数测试云函数时需要用到
    public static final String SECRET_KEY = "dde0ad23e9f6c9a8";
    // Master Key，超级权限Key。应用开发或调试的时候可以使用该密钥进行各种权限的操作
    public static final String MASTER_KAY = "69e036a32229d8cfeffb2bb04beb411b";
    //当前app的版本号，处于第一代版本
    public static final String APP_VERSION = "5";
}
