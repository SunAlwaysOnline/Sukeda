package com.example.bmobtest.Utils;

import android.text.TextUtils;

/**
 * Created by 戚春阳 on 2017/12/10.
 */

public class PhoneUtils {
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);

    }
}
