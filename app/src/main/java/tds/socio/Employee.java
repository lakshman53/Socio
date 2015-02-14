/**
 * Created by laks on 29-01-2015.
 */

package tds.socio;

import com.orm.SugarRecord;

public class Employee extends SugarRecord<Employee> {

    String EmpNumber;
    String MobileNo;
    String EmailAddress;
    String InternalEmpId;
    String Password;
    String attendanceRetValue;

    public String getAttendanceRetValue() {
        return attendanceRetValue;
    }

    public void setAttendanceRetValue(String attendanceRetValue) {
        this.attendanceRetValue = attendanceRetValue;
    }

    public String getEmpNumber() {
        return EmpNumber;
    }

    public void setEmpNumber(String empNumber) {
        EmpNumber = empNumber;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

/*  String FirstName;
    String MiddleName;
    String LastName;

    Date DateOfBirth;
    String StoreName;
    Long StoreLatitude;
    Long StoreLongitude;
    String Address;
    String City;
    String State;
    String Area;
    Date RegisteredDate;
    String DeviceId;
    Date LastLogin;
    Integer Role;
*/

    public Employee() { }

    public Employee(String EmpNumber, String MobileNo, String EmailAddress, String Password, String internalEmpId){
        this.EmpNumber = EmpNumber;
        this.MobileNo = MobileNo;
        this.EmailAddress = EmailAddress;
        this.Password = Password;
        this.InternalEmpId = internalEmpId;
    }

    public String getInternalEmpId() {
        return InternalEmpId;
    }

    public void setInternalEmpId(String internalEmpId) {
        InternalEmpId = internalEmpId;
    }
}
