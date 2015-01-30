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
    EditText textEmail, textPassword, textuserName, textMobileNumber;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Employee employee = new Employee("1234","1234");
//        employee.save();

        textPassword = (EditText) findViewById(R.id.textPassword);
        textuserName = (EditText) findViewById(R.id.textuserName);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textMobileNumber = (EditText) findViewById(R.id.textMobileNumber);
        button = (Button) findViewById(R.id.btnContinue);

        if (isRegistered())  {
            textEmail.setVisibility(View.GONE);
            textMobileNumber.setVisibility(View.GONE);
            button.setText("Continue");
        }
        else {
            textPassword.setVisibility(View.GONE);
            button.setText("Register");
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strEmpNum = textuserName.getText().toString();
               //TODO: Password Encryption
                String strPasswordencrypted = textPassword.getText().toString();
               //TODO: UI validate for username and password
                if (authenticateUser(strEmpNum,strPasswordencrypted)) {
               //TODO: In case of first login (password blank) redirect to change password screen
                    Intent mainIntent = new Intent(LoginActivity.this, AttendanceActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    finish();
                }
                else {
                    textPassword.setText("");
                    Toast.makeText(getApplicationContext(),"Incorrect Credentials, Please try again!!",Toast.LENGTH_SHORT).show();
                    textPassword.requestFocus();
                }
            }
        });
    }

    private boolean isRegistered()
    {
        List<Employee> employees = Employee.listAll(Employee.class);
        return employees.size() == 0?false:true;
    }

    private boolean authenticateUser(String userName, String Password)
    {
       Employee employee = new Employee();

       List<Employee> employees =  employee.find(Employee.class, "emp_number = ? and password = ?", userName, Password);
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
