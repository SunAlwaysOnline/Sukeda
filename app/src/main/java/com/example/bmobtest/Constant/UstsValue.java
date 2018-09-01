package com.example.bmobtest.Constant;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class UstsValue {
    /**
     * 官网主页地址
     */
    public static final String official_website = "http://www.usts.edu.cn/";

    public static final String two_options = "http://jw.usts.edu.cn/";
    /**
     * 点击教务管理系统后出现的地址，302
     */
    public static final String two_options_click = "http://jw.usts.edu.cn/default2.aspx";

    //public  String random_string;

    /**
     * 登录的网址
     */
    public static String login_url = "/default2.aspx";

    public static String bei_yong = "http://jw.usts.edu.cn/(epc5ss55od4dch55iepsj355)";

    /**
     * 验证码地址
     */
    public static String code_url = "/CheckCode.aspx";

    /**
     * 登陆成功地址栏中的地址
     */
    public static String login_success_url;
    /**
     * 登陆成功后的主界面的html
     */
    public static String login_success_main_html;

    /**
     * 个人信息的html
     */
    public static String stu_info_html;


    /**
     * 校园公告  更多
     */
    public static String news_more_lm1 = "http://notify.usts.edu.cn/news/news_more.asp?lm2=1";


    /**
     * 课外学分查询网站的验证码地址
     */
    public static String kwxfcx_code = "http://jw.usts.edu.cn/kwxfcx/SysCheckCode.aspx";
    /**
     * 课外学分查询网址
     */
    public static String kwxfcx = "http://jw.usts.edu.cn/kwxfcx/Default.aspx";

    /**
     * 课外学分查询主体页
     */

    public static String kwxfcx_main = "http://jw.usts.edu.cn/kwxfcx/main.aspx";

    /**
     * 课外学分查询详情页
     */
    public static String kwxfcx_detail = "http://jw.usts.edu.cn/kwxfcx/kccx.aspx";

    /**
     * SSL_VPN服务地址
     */
    public static String ssl_vpn = "https://vpn.usts.edu.cn/dana-na/auth/url_default/welcome.cgi";

    /**
     * SSL_VPN登陆时数据post地址
     */
    public static String ssl_login_post = "https://vpn.usts.edu.cn/dana-na/auth/url_default/login.cgi";
    /**
     * 登录成功后重定向的地址
     */
    public static String ssl_vpn_index = "https://vpn.usts.edu.cn/dana/home/index.cgi";

    /**
     * vpn登陆成功后主页里面的图片
     */
    public static String ssl_vpn_index_logo = "https://vpn.usts.edu.cn/dana-na/auth/welcome.cgi?p=rolelogo";


    /**
     * 点击校园ic卡后跳转的第一个地址
     */
    public static String card_login_1 = "https://vpn.usts.edu.cn/dana/home/launch.cgi?url=http%3A%2F%2Fcard2.usts.edu.cn%2F";
    /**
     * 点击校园ic卡后跳转的第二个地址，由上面那个地址重定向而来
     */
    public static String card_login_2 = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn,SSO=U+";
    /**
     * 一卡通登陆的真实网址
     */
    public static String card_login_url = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+homeLogin.action";

    /**
     * 一卡通验证码地址
     */
    public static String card_login_code = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+getCheckpic.action?rand=1450.6798608933456";
    /**
     * 一卡通数据提交地址
     */
    public static String card_post = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+loginstudent.action";
    /**
     * 一卡通主页的侧边栏地址，里面有当日流水的按钮
     */
    public static String card_left_frame = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+accleftframe.action";
    /**
     * 一卡通查询历史流水地址
     */
    public static String card_history = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+accounthisTrjn.action";
    /**
     * 对选定账号的一卡通历史流水查询
     */
    public static String card_history_continue = "https://vpn.usts.edu.cn/,DanaInfo=card2.usts.edu.cn+accounthisTrjn.action?__continue=";
    /**
     * 退出vpn的地址
     */
    public static String vpn_logout = "https://vpn.usts.edu.cn/dana-na/auth/logout.cgi";

    /**
     * 学生版奥蓝系统地址
     */
    public static String stu_aolan = "http://stu.usts.edu.cn/LOGIN.ASPX";
}
