package com.example.bmobtest.Utils;

import com.example.bmobtest.Bean.SlideShow;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by 戚春阳 on 2017/12/17.
 */

public interface Call {
    void onSuccess(Document document);
    void onFailure(Exception e);
}
