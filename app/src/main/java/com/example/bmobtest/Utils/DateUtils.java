package com.example.bmobtest.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 戚春阳 on 2017/12/9.
 */

public class DateUtils {
    public static String dateFormat(Date date, int tag) {
        String format = null;
        if (tag == 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = simpleDateFormat.format(date);
        }
        if (tag == 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            format = simpleDateFormat.format(date);
        }
        return format;
    }
}
