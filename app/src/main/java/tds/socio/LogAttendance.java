/**
 * Created by laks on 03-03-2015.
 */

package tds.socio;
import com.orm.SugarRecord;

public class LogAttendance extends SugarRecord<LogAttendance> {
    String Today;
    String Markedtime;
    String Logflag;

    public LogAttendance() {
    }

    public LogAttendance(String today, String markedtime, String logflag ) {
        Today = today;
        Logflag = logflag;
        Markedtime = markedtime;
    }

    public String getToday() {

        return Today;
    }

    public void setToday(String today) {
        Today = today;
    }

    public String getMarkedtime() {
        return Markedtime;
    }

    public void setMarkedtime(String markedtime) {
        Markedtime = markedtime;
    }

    public String getLogflag() {
        return Logflag;
    }

    public void setLogflag(String logflag) {
        Logflag = logflag;
    }
}
