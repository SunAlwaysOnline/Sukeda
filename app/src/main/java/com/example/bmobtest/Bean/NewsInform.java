package com.example.bmobtest.Bean;

/**
 * Created by 戚春阳 on 2018/1/26.
 */

public class NewsInform {
    private String title;
    private String time;
    private String read;
    private String first_pic_url;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getFirst_pic_url() {
        return first_pic_url;
    }

    public void setFirst_pic_url(String first_pic_url) {
        this.first_pic_url = first_pic_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsInform{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", read='" + read + '\'' +
                ", first_pic_url='" + first_pic_url + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
