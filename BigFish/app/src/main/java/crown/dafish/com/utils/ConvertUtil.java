/**********************************************************************
 * Company: 广州蛙鸣智能科技有限公司.
 * Copyright: Copyright (c) 2016
 ***********************************************************************/
package crown.dafish.com.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**********************************************************************
 * 不同数据类型转换的工具类
 *
 * @类名 ConvertUtil
 * @包名 com.frogshealth.photoframe.common.util
 * @author lijch
 * @创建日期 16/5/3
 ***********************************************************************/
public class ConvertUtil {

    /**
     * 将date类型转换为yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @param d 待转时间
     * @return 字符串格式的时间
     */
    public static String date2String(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(d);
        return strDate;
    }

    /**
     * 将date类型转换为yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @param d 待转时间
     * @return 字符串格式的时间
     */
    public static String date2StringMs(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String strDate = sdf.format(d);
        return strDate;
    }

    /**
     * 将date类型转换为format格式的时间字符串
     *
     * @param d      待转时间
     * @param format 格式
     * @return 字符串格式的时间
     */
    public static String date2String(Date d, String format) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDate = sdf.format(d);
        return strDate;
    }

    /**
     * 将string类型转换为date类型
     *
     * @param str 时间字符串
     * @return date类型的时间
     */
    public static Date string2Date(String str) {
        Date date = null;
        if (str == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * 将string类型转换为date类型
     *
     * @param str 时间字符串
     * @return date类型的时间
     */
    public static Date string2DateMs(String str) {
        Date date = null;
        if (str == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return string2Date(str);
        }
        return date;
    }


    /**
     * 将string类型转换为date类型
     *
     * @param str 时间字符串
     * @param format 格式
     * @return date类型的时间
     */
    public static Date string2Date(String str, String format) {
        Date date = null;
        if (str == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }
    /**
     * 计算起始时间以来的逝去时间(几秒前/几分前/几小时前/几天前/几周前)
     *
     * @param startTime 起始时间
     * @return 逝去时间字符串
     */
    public static CharSequence getRelativeTimeSpanString(long startTime) {
        final int WEEK_TIME = 24 * 7 * 3600 * 1000;
        CharSequence charStr = null;
        long diff = System.currentTimeMillis() - startTime;
        if (diff < WEEK_TIME) {
            charStr = DateUtils.getRelativeTimeSpanString(startTime);
        } else {
            charStr = String.valueOf(diff / WEEK_TIME) + " 周前";
        }
        return charStr;
    }

}
