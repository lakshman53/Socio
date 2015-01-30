package tds.socio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import tds.libs.StringEncrypter;

public class LoginActivity extends ActionBarActivity {
    EditText textEmail, textPassword, textuserName, textMobileNumber, textVerify, textPasswordAgain;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textPassword = (EditText) findViewById(R.id.textPassword);
        textPasswordAgain = (EditText) findViewById(R.id.textPasswordAgain);
        textuserName = (EditText) findViewById(R.id.textuserName);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textMobileNumber = (EditText) findViewById(R.id.textMobileNumber);
        textVerify = (EditText) findViewById(R.id.textVerify);

        button = (Button) findViewById(R.id.btnContinue);

        if (isRegistered())
        {
            textEmail.setVisibility(View.GONE);
            textMobileNumber.setVisibility(View.GONE);
            button.setText("Continue");
        }
        else {
            textPassword.setVisibility(View.GONE);
            textVerify.setVisibility(View.GONE);
            textPasswordAgain.setVisibility(View.GONE);
            button.setText("Register");
        }

//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//         );
    }

    public void clickButton() {
        String strEmpNum = textuserName.getText().toString();

        if (button.getText() == "Continue")
        {
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
        }
        else if (button.getText() == "Register") {
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
        }
        else if (button.getText() == "Verify")
        {
            //TODO: UI validation for verify text entry

            String strVerifyText = textVerify.getText().toString();

            //TODO: Change 12345 to received verify code

            if (checkForSameValues(strVerifyText, "12345")) {
                textVerify.setVisibility(View.GONE);
                textPassword.setVisibility(View.VISIBLE);
                textPasswordAgain.setVisibility(View.VISIBLE);
                button.setText("Save");
            }
            else {
                Toast.makeText(getApplicationContext(),"Wrong verification code!!", Toast.LENGTH_SHORT);
            }
        }
        else if (button.getText() == "Save")
        {
            //TODO: UI validation for password & verify password entry

            String strPassword, strPasswordAgain;
            strPassword = textPassword.getText().toString();
            strPasswordAgain = textPasswordAgain.getText().toString();

            if(checkForSameValues(strPassword, strPasswordAgain))
            {

                Employee employee = new Employee();
                employee.findById(Employee.class, 1L);
                employee.Password = strPassword;
                employee.save();

                //TODO: Download user details and save in database

                Intent mainIntent = new Intent(LoginActivity.this, AttendanceActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Passwords doesn't match.",Toast.LENGTH_SHORT);
            }
        }
    }

    private boolean checkForSameValues(String strEnteredCode, String strReceivedCode)
    {
        return strEnteredCode.equals(strReceivedCode)?true:false;
    }
    private boolean isUserGenuine(String strEmpNum, String strMobileNumber, String strEmail) {
        return true;
    }

    private boolean isRegistered()
    {
        List<Employee> employees = Employee.listAll(Employee.class);
        return employees.size() == 0?false:true;
    }

    private boolean authenticateUser(String userName, String Password)
    {
       Employee employee = new Employee();

       List<Employee> employees =  employee.find(Employee.class, "Emp_Number = ? and password = ?", userName, Password);
       return employees.size() == 0?false:true;
    }

    private String encryptString(String strPhrase)
    {
        try {
            SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
            StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());
            strPhrase = desEncrypter.encrypt(strPhrase);

            return strPhrase;
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("encryptString ", e.getMessage());
        }
        return null;
    }
}
