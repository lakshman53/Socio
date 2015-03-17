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
    String FirstName;
    String MiddleName;
    String LastName;
    String StoreName;
    String Address;
    String City;
    String Area;
    String Region;
    String StoreOpen;
    String StoreClose;
    String Designation;
    Integer LastOrderId;

    public Integer getLastOrderId() {
        return LastOrderId;
    }

    public void setLastOrderId(Integer lastOrderId) {
        LastOrderId = lastOrderId;
    }
    //    Long StoreLatitude;
//    Long StoreLongitude;


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

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getStoreOpen() {
        return StoreOpen;
    }

    public void setStoreOpen(String storeOpen) {
        StoreOpen = storeOpen;
    }

    public String getRegion() {
        return Region;
    }

    public String getStoreClose() {
        return StoreClose;
    }

    public void setStoreClose(String storeClose) {
        StoreClose = storeClose;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
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

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getInternalEmpId() {
        return InternalEmpId;
    }

    public void setInternalEmpId(String internalEmpId) {
        InternalEmpId = internalEmpId;
    }


/*    String DeviceId;
    Date RegisteredDate;
    Date LastLogin;
    Integer Role;
*/

    public Employee() { }



    public Employee(String empNumber, String mobileNo, String emailAddress,  String password, String internalEmpId, String firstName, String middleName, String lastName, String storeName,  String address, String city, String area, String storeOpen, String storeClose, String designation)   {
        EmpNumber = empNumber;
        MobileNo = mobileNo;
        EmailAddress = emailAddress;
        InternalEmpId = internalEmpId;
        Password = password;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        StoreName = storeName;
        Address = address;
        City = city;
        Area = area;
        StoreOpen = storeOpen;
        StoreClose = storeClose;
        Designation = designation;
        LastOrderId = 0;
    }

}
