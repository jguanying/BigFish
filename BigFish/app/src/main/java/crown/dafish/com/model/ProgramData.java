package crown.dafish.com.model;

import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 * @author sundi
 * @类名 ProgramData
 * @包名 crown.dafish.com.model
 * @创建日期 16/12/27
 ***********************************************************************/
public class ProgramData {
    /**
     * 播放日期
     */
    private String playDate;
    /**
     * 节目单
     */
    private List<ProgramModel> programs;

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public List<ProgramModel> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramModel> programs) {
        this.programs = programs;
    }
}
