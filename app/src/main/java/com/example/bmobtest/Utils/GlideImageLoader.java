package com.example.bmobtest.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bmobtest.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by 戚春阳 on 2017/12/16.
 */
public class GlideImageLoader extends ImageLoader {

    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */


        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
        // Picasso.with(context).load(path.toString()).into(imageView);

    }


}