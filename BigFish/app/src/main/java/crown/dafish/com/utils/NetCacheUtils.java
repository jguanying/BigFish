package crown.dafish.com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by jianggy on 2017/4/6.
 */

public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtil;
    private MemoryCacheUtils mMemoryCacheUtil;

    public NetCacheUtils(LocalCacheUtils localCacheUtil,
                         MemoryCacheUtils memoryCacheUtil) {
        super();
        this.mLocalCacheUtil = localCacheUtil;
        this.mMemoryCacheUtil = memoryCacheUtil;
    }

    // 从网络加载图片
    public void getBitmapFromNet(ImageView imageView, String url) {
        // imageView,url 两个参数会封装为数组传给doInBackground
        new BitmapTask().execute(imageView, url);
    }

    /**
     * AsyncTask的用法
     *
     * @author ZXJM
     * @date 2016年8月23日 三个泛型的含义：参1：doInBackground的参数类型 参2：onProgressUpdate的参数类型
     *       参3：doInBackground 加载完的返回类型，和onPostExecute 加载结束后处理的入参类型
     */
    class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {

        private ImageView imageView;
        private String url;

        /**
         * 1.预加载 ，运行在主线程
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//          System.out.println("预加载");
        }

        /**
         * 2.正在加载(核心方法)，运行在子线程
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
//          System.out.println("正在加载");
            imageView = (ImageView) params[0];
            url = (String) params[1];

            imageView.setTag(url);// 打标记

            Bitmap bitmap = download(url);
            return bitmap;
        }

        /**
         * 3.进度更新（如果下载文件），运行在主线程
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 4.加载结束，运行在主线程
         */
        @Override
        protected void onPostExecute(Bitmap result) {
//          System.out.println("加载结束");

            // 由于listview的重用机制导致imageview对象可能被多个item共用,
            // 从而可能将错误的图片设置给了imageView对象
            // 所以需要在此处校验, 判断是否是正确的图片
            if (result != null) {
                String url = (String) imageView.getTag();
                if (url != null && url.equals(this.url)) {
                    // 从网络加载图片
                    imageView.setImageBitmap(result);
                    System.out.println("从网络加载图片啦.....");
                    // 写本地缓存
                    mLocalCacheUtil.setLocalCache(url, result);
                    //写内存缓存
                    mMemoryCacheUtil.setMemoryCache(url, result);
                }
            }
        }
    }

    /**
     * 根据url下载图片
     *
     * @param url
     * @return
     */
    public Bitmap download(String url) {
        HttpURLConnection conn;
        try {
            URL murl = new URL(url);
            conn = (HttpURLConnection) murl.openConnection();
            conn.setRequestMethod("GET");// 请求方式，必须大写
            conn.setConnectTimeout(5000);// 连接超时时间
            conn.setReadTimeout(5000);// 读取超时时间
            conn.connect();// 开始连接
            int code = conn.getResponseCode();// 获取返回码

            if (code == 200) {
                // 成功后获取流，进行处理
                InputStream is = conn.getInputStream();
                // 根据流来获取对应的数据，这里是图片，所以直接根据流得到bitmap对象
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
