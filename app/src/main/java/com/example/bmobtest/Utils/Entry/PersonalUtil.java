package com.example.bmobtest.Utils.Entry;

import android.util.Log;

import com.example.bmobtest.Bean.User;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 戚春阳 on 2018/2/8.
 */

public class PersonalUtil {
    //由user的id得到其个人相关信息，不包括其发表的词条数，与获赞总数
    public static void get_personal_info(String objectId, final get_personal_infoCall call) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        call.success(list);
                    } else {
                        call.fail();
                        RxToast.error("此人或已经被删除！");
                    }
                } else {
                    Log.e("tag", e.toString());
                    e.printStackTrace();
                    call.fail();
                    RxToast.error("查询失败");
                }
            }
        });
    }


    public interface get_personal_infoCall {
        void success(List<User> list);

        void fail();
    }
}
