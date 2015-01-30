/**
 * Created by laks on 29-01-2015.
 */

package tds.socio;

import com.orm.SugarRecord;
import java.util.Date;

public class Employee extends SugarRecord<Employee> {

    String empNumber;
    String firstName;
    String middleName;
    String lastName;
    String Password;
    String MobileNo;
    String EmailAddress;
    Date DOB;
    String StoreName;
    Long StoreLatitude;
    Long StoreLongitude;
    String Address;
    String City;
    String State;
    String Area;
    Date RegisteredDate;
    Date LastLogin;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public Long getStoreLatitude() {
        return StoreLatitude;
    }

    public void setStoreLatitude(Long storeLatitude) {
        StoreLatitude = storeLatitude;
    }

    public Long getStoreLongitude() {
        return StoreLongitude;
    }

    public void setStoreLongitude(Long storeLongitude) {
        StoreLongitude = storeLongitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public Date getRegisteredDate() {
        return RegisteredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        RegisteredDate = registeredDate;
    }

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
    }

    public Employee() {

    }

    public Employee(String empNumber, String password)
    {
        this.empNumber = empNumber;
        this.firstName = password;
    }


}
