package com.example.bmobtest.Utils.Entry;

import com.example.bmobtest.Bean.Entry;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 戚春阳 on 2018/2/7.
 */

public class EntryDetailUtil {


    public static void delete_entry(final String categoryId, String entryId, final deleteCall deleteCall) {
        Entry entry = new Entry();
        entry.setObjectId(entryId);
        entry.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //删除成功后，使得当前所属的词条分类的的词条总数-1
                    EntryAddUtil.decreaseOne_category(categoryId);
                    deleteCall.success();
                } else {
                    deleteCall.fail();
                }
            }
        });

    }


    //根据entryId来获取对应词条的内容，主要是为了及时更新数据
    public static void update_entry(String entryId, final update_entryCall call) {
        BmobQuery<Entry> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", entryId);
        //并把其所对应的作者的名称及objectId
        query.include("author[objectId|username|file]");
        query.findObjects(new FindListener<Entry>() {
            @Override
            public void done(List<Entry> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        call.success(list);
                    } else {
                        call.fail();
                        RxToast.error("当前词条已经被删除！");
                    }
                } else {
                    call.fail();
                   // RxToast.error("查询词条失败！");
                }
            }
        });


    }

    public interface deleteCall {
        void success();

        void fail();
    }


    public interface update_entryCall {
        void success(List<Entry> list);

        void fail();
    }


}
