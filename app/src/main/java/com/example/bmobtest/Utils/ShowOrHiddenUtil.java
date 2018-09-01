package com.example.bmobtest.Utils;

import android.app.Activity;
import android.view.View;

import com.example.bmobtest.R;

/**
 * Created by 戚春阳 on 2017/12/14.
 */

public class ShowOrHiddenUtil {
    public static void show_home_bottom(Activity activity) {
        activity.findViewById(R.id.tab).setVisibility(View.VISIBLE);
    }

    public static void hidden_home_bottom(Activity activity) {
        activity.findViewById(R.id.tab).setVisibility(View.GONE);
    }

    public static int get_bottom(Activity activity){
        return activity.findViewById(R.id.tab).getVisibility();
    }

}
