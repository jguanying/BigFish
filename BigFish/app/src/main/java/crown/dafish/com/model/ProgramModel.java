package crown.dafish.com.model;

/**********************************************************************
 * 节目单
 * @author sundi
 * @类名 ProgramModel
 * @包名 crown.dafish.com.model
 * @创建日期 16/12/27
 ***********************************************************************/
public class ProgramModel {
    /**
     * 节目ID
     */
    private String programId;
    /**
     * 节目名
     */
    private String programName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
