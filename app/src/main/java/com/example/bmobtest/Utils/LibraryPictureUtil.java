package com.example.bmobtest.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 戚春阳 on 2018/1/24.
 */

public class LibraryPictureUtil {
    public static int choose = 1;

    public static List<String> get_library_pic_title() {
        List<String> title = new ArrayList<>();
        title.add("图书馆");
        title.add("电子阅览室");
        title.add("石湖讲坛");
        //title.add("图书馆美景01");
        title.add("图书馆美景01");
        //title.add("图书馆美景02");
        title.add("图书馆美景02");
        title.add("图书馆美景03");
        //title.add("图书馆美景06");
        title.add("图书馆美景04");
        title.add("图书馆美景05");
        //title.add("图书馆美景09");
        //title.add("图书馆美景10");
        title.add("一楼大厅");
        title.add("进门门禁");
        title.add("总服务台");
        title.add("自助借还机");
        title.add("自助查询机");
        title.add("借阅图书室");
        //title.add("借阅图书室之二");
        //title.add("借阅图书室之三");
        title.add("自习室");
        title.add("研修室");
        title.add("二楼自习大厅");
        title.add("二楼大厅");
        title.add("期刊阅览室");
        title.add("特藏室");
        //title.add("特藏室02");
        //title.add("特藏室02");
        //title.add("特藏室03");

        return title;
    }

    public static List<String> get_library_pic_url_dim() {
        List<String> url = new ArrayList<>();
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/088.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/IMG_6386.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC_0084.jpg");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01160.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01165.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01171.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01170.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01175.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01183.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01184.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01194.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01197.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01199.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01138.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01144.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01147.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01148.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01145_0.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01152.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01153.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01159.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01158.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01154.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01136.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01137.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01134.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01122.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01125.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01126.JPG");
        url.add("http://library.usts.edu.cn/?q=sites/default/files/imagecache/Slideshow/DSC01127.JPG");

        return url;
    }

    public static List<String> get_library_pic_url_clear() {
        List<String> url = new ArrayList<>();
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/088.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/img_6386.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/DSC_0084.jpg");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01160.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01165.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01171.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01170.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01175.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01183.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01184.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01194.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01197.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01199.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01138.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01144.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01147.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01148.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/slideshow/DSC01145.jpg");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01152.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01153.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01159.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01158.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01154.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01136.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01137.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01134.JPG");
        url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01122.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01125.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01126.JPG");
        //url.add("http://library.usts.edu.cn/sites/default/files/Slideshow/DSC01127.JPG");

        return url;
    }

    public static List<String> get_library_picture_url() {
        if (choose == 0) {
            return get_library_pic_url_dim();
        } else {
            return get_library_pic_url_clear();
        }

    }


}
