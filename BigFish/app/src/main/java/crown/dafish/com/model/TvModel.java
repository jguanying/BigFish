package crown.dafish.com.model;

/**********************************************************************
 * @author sundi
 * @类名 TvModel
 * @包名 crown.dafish.com.model
 * @创建日期 16/12/26
 ***********************************************************************/
public class TvModel {
    /**
     * 频道播放URL
     */
    private String mUrl;
    /**
     * 频道名称
     */
    private String mTvName;

    /**
     * 频道ID
     */
    private String mId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getTvName() {
        return mTvName;
    }

    public void setTvName(String tvName) {
        mTvName = tvName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
