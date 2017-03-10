package crown.dafish.com.model;

/**********************************************************************
 * 节目信息
 * @author sundi
 * @类名 ProgramInfo
 * @包名 crown.dafish.com.model
 * @创建日期 16/1/1
 ***********************************************************************/
public class ProgramInfo {

    private String logo;

    private String title;

    private String source;

    public String getBackupSource() {
        return backupSource;
    }

    public void setBackupSource(String backupSource) {
        this.backupSource = backupSource;
    }

    private String backupSource;

    private String epg;

    private String uuid;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpg() {
        return epg;
    }

    public void setEpg(String epg) {
        this.epg = epg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
