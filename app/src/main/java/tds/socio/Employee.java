/**
 * Created by laks on 29-01-2015.
 */

package tds.socio;

import com.orm.SugarRecord;
import java.util.Date;

public class Employee extends SugarRecord<Employee> {

    String EmpNumber;
    String Password;
    String FirstName;
    String MiddleName;
    String LastName;
    String MobileNo;
    String EmailAddress;
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

    public Employee() { }

    public Employee(String EmpNumber, String MobileNo, String EmailAddress){
        this.EmpNumber = EmpNumber;
        this.MobileNo = MobileNo;
        this.EmailAddress = EmailAddress;
    }
}
