package com.example.bmobtest.Utils;

import android.webkit.WebView;

/**
 * Created by 戚春阳 on 2018/1/1.
 */

public class NewsReset {

    /**
     * 将图片缩放到与屏幕等宽
     */
    public static void imgReset(WebView mWebView) {
        mWebView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "     img.align='center';   "
                + "    img.style.width = 'auto';   "
                + "    img.style.height = '240px';   "
                + "}" + "})();");
    }

    /**
     * 去除搜当前时间显示，搜索，头部,当前位置,打印本页，关闭窗口
     */
    public static void deleteContent_news(WebView mWebView) {
        mWebView.loadUrl("javascript:function deleteSearch(){" +
                "document.getElementsByClassName('search')[0].remove();" +
                "document.getElementsByClassName('head')[0].remove();" +
                "document.getElementsByClassName('banner')[0].remove();" +
                "document.getElementsByClassName('local_box')[0].remove();" +
                "document.getElementsByClassName('blank_1')[0].remove();" +
                "document.getElementsByClassName('blank')[0].remove();" +
                "document.querySelector('body > div.Max_content > table > tbody > tr:nth-child(1) > td').remove();" +
                "document.querySelector('body > div.Max_content > table > tbody > tr:nth-child(3) > td').remove();" +
                "document.getElementsByClassName('yqlj')[0].remove();" +
                " }  javascript:deleteSearch();");
    }

    public static void deleteContent_notify(WebView mWebView) {
        mWebView.loadUrl("javascript:function deleteSearch(){" +
                "document.getElementsByClassName('bg')[0].remove();" +
                "document.getElementsByClassName('menu')[0].remove();" +
                "document.querySelector('#ggtu').remove();" +
                "document.getElementsByClassName('local_position')[0].remove();" +
                "document.getElementsByClassName('title')[0].remove();" +
                "document.getElementsByClassName('author')[0].remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td').remove();" +
                "document.getElementsByClassName('footer')[0].remove();" +
                " }  javascript:deleteSearch();");
    }

    public static void deleteContent_notify1(WebView mWebView) {
        mWebView.loadUrl("javascript:function deleteSearch(){" +
                "document.querySelector('body > div.bg').remove();" +
                "document.querySelector('body > div.wp > div.menu').remove();" +
                "document.querySelector('#ggtu').remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div.shut').remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div:nth-child(4)').remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div.hot_news').remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div:nth-child(6)').remove();" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div:nth-child(7);" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > div:nth-child(8);" +
                "document.querySelector('#main > div.Max_content > table > tbody > tr:nth-child(3) > td > table;" +
                "document.querySelector('#main > div.footer').remove();" +
                "document.querySelector('#main > div.footer > div.footerLeft').remove();" +
                "document.querySelector('#main > div.footer > div.footerrig_all').remove();" +
                " }  javascript:deleteSearch();");
    }

    /**
     * 改变文字大小
     */
    public static void changeSize() {

    }

    //禁用图片的点击事件
    public static void click(WebView mWebView) {
        mWebView.loadUrl("javascript:function picclick(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "imgs[i].onclick=function(){ return false; } }" +
                "} javascript:picclick();");
    }

    //查看大图
    public static void addImageClickListner(WebView mWebView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public static void delete(WebView mWebView) {
        mWebView.loadUrl("javascript:function deleteSearch(){" +
                "document.querySelector('body > div.box_all').remove();" +
                "document.querySelector('body > div.bg1').remove();" +
                "document.querySelector('body > div.com_ban').remove();" +
                "document.querySelector('body > div.common > div > div.list').remove();" +
                "document.querySelector('body > div.common > div > div.com_rt > div.com_rt_title').remove();" +
                "document.querySelector('body > div.bg2').remove();" +
                " }  javascript:deleteSearch();");
    }
}
