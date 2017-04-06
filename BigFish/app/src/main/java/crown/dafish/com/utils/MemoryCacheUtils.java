package crown.dafish.com.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

/**
 * 内存缓存工具类
 * 1.第一次优化，利用软引用解决可能的OOM异常
 *
 * @author ZXJM
 * @date 2016年8月23日
 *
 */
public class MemoryCacheUtils {

    //  private HashMap<String, Bitmap> hash;
    private HashMap<String, SoftReference<Bitmap>> hash;

    // 写内存缓存
    public void setMemoryCache(String url, Bitmap bitmap) {
//      if (hash == null) {
//          hash = new HashMap<String, Bitmap>();
//      }
//      hash.put(url, bitmap);
        if(hash == null){
            hash = new HashMap<String, SoftReference<Bitmap>>();
        }
        //使用软引用把Bitmap包装起来
        SoftReference<Bitmap> soft = new SoftReference<Bitmap>(bitmap);
        hash.put(url, soft);
    }

    // 读内存缓存
    public Bitmap getMemoryCache(String url) {
//      if (hash != null && hash.containsKey(url)) {
//          Bitmap bitmap = hash.get(url);
//          return bitmap;
//      }

        if(hash!=null && hash.containsKey(url)){
            SoftReference<Bitmap> soft = hash.get(url);
            Bitmap bitmap = soft.get();
            return bitmap;
        }
        return null;
    }

}
