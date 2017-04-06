package crown.dafish.com.utils;

/**
 * Created by jianggy on 2017/4/6.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {
    private static final String LOCAL_CACHE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()+"/BigFish_cache";

    //写本地缓存
    public void setLocalCache(String url,Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if(!dir.exists() || !dir.isDirectory()){
            dir.mkdirs();//创建文件夹
        }

        try {
            String fileName = MD5Encoder.encode(url);;//采用MD5加密文件名
            File cacheFile = new File(dir, fileName);
            // 参1:图片格式;参2:压缩比例0-100; 参3:输出流
            bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读本地缓存
    public Bitmap getLocalCache(String url) {
        try {
            File cacheFile = new File(LOCAL_CACHE_PATH, MD5Encoder.encode(url));
            if(cacheFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
