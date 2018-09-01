package com.example.bmobtest.Utils;

import com.example.bmobtest.Bean.SlideShow;

import java.util.List;

/**
 * Created by 戚春阳 on 2017/12/16.
 */

public interface CallBackListener {
    void onSuccess(List<SlideShow> list);
    void onFailure(Exception e);


}
