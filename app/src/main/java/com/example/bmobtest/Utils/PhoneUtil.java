package com.example.bmobtest.Utils;

import android.util.Log;
import android.widget.LinearLayout;

import com.example.bmobtest.Bean.DepartmentPhone;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by 戚春阳 on 2018/2/16.
 */

public class PhoneUtil {
    //模糊搜索，由职位或办公地点查询查询号码
    public static void get_phone(String text, final getPhone_mohuCall call) {
        BmobQuery<DepartmentPhone> query = new BmobQuery<>();
        query.addWhereMatches("position", text);
        query.findObjects(new FindListener<DepartmentPhone>() {
            @Override
            public void done(List<DepartmentPhone> list, BmobException e) {
                if (e == null) {
                    call.success(list);
                } else {
                    call.fail();
                }
            }
        });
    }

    //获取当前所有的部门或单位,不重复
    public static void getDepartment(final getDepartmentCall call) {
        String sql = "select distinct department from DepartmentPhone";
        new BmobQuery<DepartmentPhone>().doSQLQuery(sql, new SQLQueryListener<DepartmentPhone>() {
            @Override
            public void done(BmobQueryResult<DepartmentPhone> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<DepartmentPhone> list = bmobQueryResult.getResults();
                    call.success(list);
                } else {
                    call.fail();
                }

            }
        });

    }
          //查询当前部门下的所有职位
    public static void get_position_by_department(String department, final getPositionCall call) {
        BmobQuery<DepartmentPhone> query = new BmobQuery<>();
        query.addWhereEqualTo("department", department);
        query.findObjects(new FindListener<DepartmentPhone>() {
            @Override
            public void done(List<DepartmentPhone> list, BmobException e) {
                if (e == null) {
                    call.success(list);
                } else {
                    call.fail();
                }

            }
        });

    }

    public interface getPhone_mohuCall {
        void success(List<DepartmentPhone> list);

        void fail();
    }

    public interface getDepartmentCall {
        void success(List<DepartmentPhone> list);

        void fail();
    }

    public interface getPositionCall {
        void success(List<DepartmentPhone> list);

        void fail();
    }
}


