////////////////////////////////////////////////////////////////////////////////
// system:Guiyangandroid
//
// file name: LocalCacheUtil.java
// class name: LocalCacheUtil
//
// Ver.   date       auther           comment
//-----------------------------------------------------------------------------
// V1.00  2014/07/30 li.dg
////////////////////////////////////////////////////////////////////////////////

package crown.dafish.com.utils;

import java.io.File;
import org.apache.commons.lang.StringUtils;
import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class LocalCacheUtil {

    /**
     * log
     */
//    private static Logger log = LoggerFactory.getLogger(LocalCacheUtil.class);

    /**
     * Constructor
     */
    private LocalCacheUtil() {
    }

    /**
     * Home目录做成
     * 
     * @param ctx
     *            Context
     * @return Home目录路径
     * 
     * 有些手机会自动清理缓存，容易将cache目录删除，而我们的数据库也在此目录下，所以会导致用户的登录状态丢失
     * 不过改成其它目录又会导到文件目录无法删除的问题
     * 
     */
    public static String getHomeDir(final Context ctx) {
    	
    	final String cacheDir = "/Android/data/" + ctx.getPackageName() + "/cache/";
    	
        File sdDir = null;
        final boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            final File f = new File(sdDir.getPath() + cacheDir);
            if (!f.exists()) {
                final boolean isCreated = f.mkdirs();
                if (!isCreated) {
//                    Log.e("LocalCacheUtil.getHomeDir", "don't create home directories");
                }
            }
            return f.getPath();
        } else {
            return ctx.getFilesDir().getAbsolutePath();
        }
    }

    /**
     * 异常日志目录做成
     * 
     * @param ctx
     *            Context
     * @return 异常日志目录路径
     */
    public static String getLogDir(final Context ctx) {
        String homeDirPaht = getHomeDir(ctx);
        File f = new File(homeDirPaht + Constant.LOG_DIR);
        if (!f.exists()) {
            final boolean isCreated = f.mkdirs();
            if (!isCreated) {
//                log.warn("don't create log directories");
            }
        }
        return f.getPath();
    }

    /**
     * 缓存数据库目录做成
     * 
     * @param ctx
     *            Context
     * @return 缓存数据库目录路径
     */
    public static String getDbDir(final Context ctx) {
        String homeDirPaht = getHomeDir(ctx);
        File f = new File(homeDirPaht + Constant.DB_DIR);
        if (!f.exists()) {
            final boolean isCreated = f.mkdirs();
            if (!isCreated) {
//                log.warn("don't create db directories");
            }
        }
        return f.getPath();
    }

    /**
     * 图片缓存目录做成
     * 
     * @param ctx
     *            Context
     * @return 图片缓存目录路径
     */
    public static String getCacheImgDir(final Context ctx) {
        String homeDirPaht = getHomeDir(ctx);
        File f = new File(homeDirPaht + Constant.IMG_DIR);
        if (!f.exists()) {
            final boolean isCreated = f.mkdirs();
            if (!isCreated) {
//                log.warn("don't create image directories");
            }
        }
        return f.getPath();
    }
    
    /**
     * 取得sd下的图片缓存路径
     * 
     * @param ctx
     *            Context
     * @return 图片缓存目录路径
     */
    public static String getCacheImgPath(final Context ctx) {
        String cacheDir = "/Android/data/" + ctx.getPackageName() + "/cache/img";
        return cacheDir;
    }

    /**
     * 离线包目录做成
     * 
     * @param ctx
     *            Context
     * @return 离线包目录路径
     */
    public static String getOfflineDir(final Context ctx) {
        String homeDirPaht = getHomeDir(ctx);
        File f = new File(homeDirPaht + Constant.OFFLINE_DIR);
        if (!f.exists()) {
            final boolean isCreated = f.mkdirs();
            if (!isCreated) {
//                log.warn("don't create offline directories");
            }
        }
        return f.getPath();
    }

    /**
     * 返回路径中的末端名字 例如：/aaa/bbb/ccc 返回 ccc
     * 
     * @param path
     * @return
     */
    public static String getPathLastName(String path) {
        int index = StringUtils.lastIndexOf(path, "/");
        if (index < 0) {
            return "";
        }
        return StringUtils.substring(path, index + 1);
    }

    /**
     * 图片下载目录做成
     * 
     * @param ctx
     *            Context
     * @return Home目录路径
     */
    public static String getImagesDir(final Context ctx) {
        
        File sdDir = null;
        final boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            final File f = new File(sdDir.getPath() + Constant.IMAGE_DOWNLOAD_PATH);
            if (!f.exists()) {
                final boolean isCreated = f.mkdirs();
                if (!isCreated) {
//                    Log.e("LocalCacheUtil.getHomeDir", "don't create home directories");
                }
            }
            return f.getPath();
        } else {
            return ctx.getFilesDir().getAbsolutePath();
        }
    }
    
    /**
     * 拍照图片缓存目录做成
     * 
     * @param ctx
     *            Context
     * @return Home目录路径
     */
    public static String getCameraImgDir(final Context ctx) {
        
        String homeDirPaht = getHomeDir(ctx);
        File f = new File(homeDirPaht + Constant.CAMERA_DIR);
        if (!f.exists()) {
            final boolean isCreated = f.mkdirs();
            if (!isCreated) {
//                log.warn("don't create offline directories");
            }
        }
        return f.getPath();
    }
}
