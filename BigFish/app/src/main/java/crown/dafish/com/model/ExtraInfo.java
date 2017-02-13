package crown.dafish.com.model;

/**********************************************************************
 * @author sundi
 * @类名 ExtraInfo
 * @包名 crown.dafish.com.model
 * @创建日期 16/1/1
 ***********************************************************************/
public class ExtraInfo {

    private String version;

    private String customer;

    private String logo;

    private String background;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
