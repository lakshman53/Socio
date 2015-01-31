public class onClickClass {

    /*
public void clickButton() {

    t0y {
    String strEmpNum = textuserName.getText().toString();

    if (button.getText() == "Continue") {
        //TODO: Password Encryption
        String strPasswordencrypted = textPassword.getText().toString();
        //TODO: UI validation for login
        if (authenticateUser(strEmpNum, strPasswordencrypted)) {
            //TODO: In case of first login (password blank) redirect to change password screen
            Intent mainIntent = new Intent(LoginActivity.this, AttendanceActivity.class);
            LoginActivity.this.startActivity(mainIntent);
            finish();
        } else {
            textPassword.setText("");
            Toast.makeText(getApplicationContext(), "Incorrect Credentials, Please try again!!", Toast.LENGTH_SHORT).show();
            textPassword.requestFocus();
        }
    } else if (button.getText() == "Register") {
        //TODO: UI validation for Registration fields entry

        String strEmail, strMobileNumber;

        strMobileNumber = textMobileNumber.getText().toString();
        strEmail = textEmail.getText().toString();

        //TODO: Check if user is genuine with employee number, email and mobile number

        if (isUserGenuine(strEmpNum, strMobileNumber, strEmail)) {

            Employee employee = new Employee(strEmpNum, strMobileNumber, strEmail);
            employee.save();

            //TODO: Send a random code and check if the mobile no. is correct.
            //TODO: LOW PRIORITY: Automatically check the sms received.

            textVerify.setVisibility(View.VISIBLE);
            button.setText("Verify");
        }
    } else if (button.getText() == "Verify") {
        //TODO: UI validation for verify text entry

        String strVerifyText = textVerify.getText().toString();

        //TODO: Change 12345 to received verify code

        if (checkForSameValues(strVerifyText, "12345")) {
            textVerify.setVisibility(View.GONE);
            textPassword.setVisibility(View.VISIBLE);
            textPasswordAgain.setVisibility(View.VISIBLE);
            button.setText("Save");
        } else {
            Toast.makeText(getApplicationContext(), "Wrong verification code!!", Toast.LENGTH_SHORT);
        }
    } else if (button.getText() == "Save") {
     //TODO: UI validation for password & verify password entry

        String strPassword, strPasswordAgain;
        strPassword = textPassword.getText().toString();
        strPasswordAgain = textPasswordAgain.getText().toString();

        if (checkForSameValues(strPassword, strPasswordAgain)) {

            Employee employee = new Employee();
            employee.findById(Employee.class, 1L);
            employee.Password = strPassword;
            employee.save();

            //TODO: Download user details and save in database

            Intent mainIntent = new Intent(LoginActivity.this, AttendanceActivity.class);
            LoginActivity.this.startActivity(mainIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Passwords doesn't match.", Toast.LENGTH_SHORT);
        }
    }
} catch (Exception ex) {
    Log.e("Onclick: ", ex.getMessage());
}
    }
*/
}