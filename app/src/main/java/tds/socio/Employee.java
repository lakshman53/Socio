
/**
 * Created by laks on 29-01-2015.
 */

package tds.socio;

import com.orm.SugarRecord;

public class Employee extends SugarRecord<Employee> {

    String empNumber;
    String firstName;
    String middleName;
    String lastName;

    public Employee() {

    }

    public Employee(String empNumber, String firstName, String middleName, String lastName)
    {
        this.empNumber = empNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
