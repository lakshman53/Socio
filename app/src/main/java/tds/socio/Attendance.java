package tds.socio;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by laks on 05-04-2015.
 */

public class Attendance extends SugarRecord<Attendance> {

    Date logDateTime;
    String Flag;
    Shift shift;

    public Attendance() {    }

    public Attendance(Date logDateTime, String flag, Shift shift) {
        this.logDateTime = logDateTime;
        Flag = flag;
        this.shift = shift;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
