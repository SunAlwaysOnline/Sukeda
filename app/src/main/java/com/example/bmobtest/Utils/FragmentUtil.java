package com.example.bmobtest.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by 戚春阳 on 2017/12/16.
 */

public class FragmentUtil {
    public static void switchFragment(FragmentManager fragmentManager, int from_content, Fragment to, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(from_content);
        Log.e("tag","现存"+fragment.toString());
        if (to.isAdded()) {
            fragmentTransaction.hide(fragment).show(to);
            Log.e("tag","已经添加"+to.toString());
        } else {
            fragmentTransaction.hide(fragment).add(from_content, to);
            Log.e("tag","未添加"+to.toString());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null).commit();
        } else {
            fragmentTransaction.commit();
        }


    }
}
