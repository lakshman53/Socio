package tds.socio;

import com.orm.SugarRecord;

/**
 * Created by laks on 03-03-2015.
 */
public class LogAttendance extends SugarRecord<LogAttendance> {
    String Today;
    String Logintime;
    String Logouttime;

    public String getToday() {
        return Today;
    }

    public void setToday(String today) {
        Today = today;
    }

    public String getLogintime() {
        return Logintime;
    }

    public void setLogintime(String logintime) {
        Logintime = logintime;
    }

    public String getLogouttime() {
        return Logouttime;
    }

    public void setLogouttime(String logouttime) {
        Logouttime = logouttime;
    }

    public LogAttendance() {}
    public LogAttendance(String today, String logintime, String logouttime) {
        Today = today;
        Logintime = logintime;
        Logouttime = logouttime;
    }
}
