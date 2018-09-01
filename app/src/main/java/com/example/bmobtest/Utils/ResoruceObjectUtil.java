package com.example.bmobtest.Utils;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class ResoruceObjectUtil {
    public static Object getObject(Activity activity, String name) {
        Resources res = activity.getResources();
        return res.getIdentifier(name, "drawable", activity.getPackageName());
    }
}
