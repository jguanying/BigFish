package crown.dafish.com.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 * @author sundi
 * @类名 Util
 * @包名 crown.dafish.com.utils
 * @创建日期 16/12/27
 ***********************************************************************/
public class Util {

    /**
     * 取得显示信息
     *
     * @param context Activity
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        if (context instanceof Activity) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        return metrics;
    }

    /**
     * 当时间不是两位时,用0进行填充,例如:1:21--->01:21
     *
     * @param s :时间值
     * @return 格式化后的值
     */
    public static String formatWithZero(String s) {
        if (s.length() == 1) {
            s = String.format("0%s", s);
        }

        return s;
    }


    /**
     * dp转换px
     *
     * @param context :context
     * @param dp      :dp值
     * @return px值
     */
    public static int dp2px(Context context, int dp) {
        if (dp == 0) {
            return 0;
        }
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);

    }


    /**
     * px转换dp
     *
     * @param context :context
     * @param px      :px值
     * @return dp值
     */
    public static int px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(px / density);
    }

    /**
     * sp转换px
     *
     * @param context :context
     * @param sp      :sp值
     * @return px值
     */
    public static int sp2px(Context context, int sp) {
        if (sp == 0) {
            return 0;
        }
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    /**
     * px转换sp
     *
     * @param context :context
     * @param px      :px值
     * @return sp值
     */
    public static int px2sp(Context context, int px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) Math.ceil(px / scaledDensity);
    }

    /**
     * 获取内置SD卡路径
     * @return
     */
    public static  String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取外置SD卡路径
     * @return	应该就一条记录或空
     */
    public static  List getExtSDCardPath()
    {
        List lResult = new ArrayList();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard"))
                {
                    String [] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory())
                    {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }
}
