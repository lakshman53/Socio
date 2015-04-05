package tds.socio;

import com.orm.SugarRecord;
import java.util.Date;

/**
 * Created by laks on 05-04-2015.
 */

public class Shift extends SugarRecord<Shift> {

    String shiftName;
    Date startTime, EndTime;

    public Shift(String shiftName, Date startTime, Date endTime) {
        this.shiftName = shiftName;
        this.startTime = startTime;
        EndTime = endTime;
    }

    public Shift() {
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }
}
