package crown.dafish.com.model;

import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 * @author sundi
 * @类名 Channel
 * @包名 crown.dafish.com.model
 * @创建日期 16/1/1
 ***********************************************************************/
public class Channel {


    private ArrayList<ProgramInfo> programs;

    private ExtraInfo info;

    public ArrayList<ProgramInfo> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<ProgramInfo> programs) {
        this.programs = programs;
    }

    public ExtraInfo getExtraInfo() {
        return info;
    }

    public void setExtraInfo(ExtraInfo extraInfo) {
        info = extraInfo;
    }
}
