package cn.droidlover.xrichtext;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by wanglei on 2016/11/02.
 */

public interface ImageLoader {
    Bitmap getBitmap(String url) throws IOException, ExecutionException, InterruptedException;
}
