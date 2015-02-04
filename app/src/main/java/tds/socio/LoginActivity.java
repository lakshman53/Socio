package tds.socio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import tds.libs.StringEncrypter;

public class LoginActivity extends ActionBarActivity {
    EditText textEmail, textPassword, textuserName, textMobileNumber, textVerify, textPasswordAgain;
    Button button;
    String strEmail = "", strMobileNumber = "";
    String verifyCode = "";

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
            textVerify.setVisibility(View.GONE);
            textPasswordAgain.setVisibility(View.GONE);
            button.setText("Continue");
        }
        else {
            textPassword.setVisibility(View.GONE);
            textVerify.setVisibility(View.GONE);
            textPasswordAgain.setVisibility(View.GONE);
            button.setText("Register");
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strEmpNum = textuserName.getText().toString();

               if (button.getText() == "Continue")
               {
                   //TODO: Password Encryption
                   String strPasswordencrypted = textPassword.getText().toString();
                   //TODO: UI validation for login
                   if (authenticateUser(strEmpNum, strPasswordencrypted)) {
                       //TODO: In case of first login (password blank) redirect to change password screen
                       Intent mainIntent = new Intent(LoginActivity.this, MyProfileActivity.class);
                       LoginActivity.this.startActivity(mainIntent);
                       finish();
                   } else {
                       textPassword.setText("");
                       Toast.makeText(getApplicationContext(), "Incorrect Credentials, Please enter again!!", Toast.LENGTH_SHORT).show();
                       textPassword.requestFocus();
                   }
               }
                else if (button.getText() == "Register") {
                   //TODO: UI validation for Registration fields entry

                   strMobileNumber = textMobileNumber.getText().toString();
                   strEmail = textEmail.getText().toString();

                 //  if (isUserGenuine(strEmpNum, strMobileNumber, strEmail)) {
                AsyncCallWS isUserGenuineTask = new AsyncCallWS();
                try {

                verifyCode = isUserGenuineTask.execute(new String[]{strEmpNum,strMobileNumber,strEmail }).get();

                    Toast.makeText(getApplicationContext(),verifyCode,Toast.LENGTH_LONG).show();

                if (!verifyCode.equals("wrong")){

                       //TODO: Send a random code as SMS and check if the mobile no. is correct.
                       //TODO: LOW PRIORITY: Automatically check the sms received.

                       textVerify.setVisibility(View.VISIBLE);
                       button.setText("Verify");
                       Toast.makeText(getApplicationContext(),"Verification Code: " + verifyCode.split(",",2)[1], Toast.LENGTH_LONG).show();
                }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"No employee exists with these credentials!!",Toast.LENGTH_LONG).show();
                   }
                   }
                    catch (Exception e)
                    {
                        Log.e("Employee Registration Async", e.getMessage());
                    }
               }
               else if (button.getText() == "Verify")
               {
                       //TODO: UI validation for verify text entry

                        String strVerifyText = textVerify.getText().toString();

                        if (checkForSameValues(strVerifyText, verifyCode.split(",",2)[1])) {

                           textVerify.setVisibility(View.GONE);
                           textPassword.setVisibility(View.VISIBLE);
                           textPasswordAgain.setVisibility(View.VISIBLE);
                           textEmail.setVisibility(View.GONE);
                           textMobileNumber.setVisibility(View.GONE);

                            //SharedPreferences.Editor editor = getSharedPreferences("EMP_DETAIL", MODE_PRIVATE).edit();
                            //editor.putString("intEmpId", verifyCode.split(",",2)[0]);
                            //editor.commit();

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
                           Toast.makeText(getApplicationContext(), verifyCode.split(",",2)[0], Toast.LENGTH_LONG).show();
                           Employee employee = new Employee(strEmpNum, strMobileNumber, strEmail, strPassword, verifyCode.split(",",2)[0].toString());
                           employee.save();

                           //TODO: Download user details and save in database

                           Intent mainIntent = new Intent(LoginActivity.this, MyProfileActivity.class);
                           LoginActivity.this.startActivity(mainIntent);
                           finish();
                       }
                       else
                       {
                        Toast.makeText(getApplicationContext(),"Passwords doesn't match.",Toast.LENGTH_SHORT);
                       }
                   }
               }
        });
    }

    private boolean checkForSameValues(String strEnteredCode, String strReceivedCode)
    {
        return strEnteredCode.equals(strReceivedCode)?true:false;
    }
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://sociowebservice.azurewebsites.net/RegAuthenticate.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static class WebService {

        public static String isUserGenuine(String strEmpNum, String strMobileNumber, String strEmail) {

        String resTxt = "-1";
        SoapObject request = new SoapObject(NAMESPACE, "isEmployeeExists");
        PropertyInfo sayHelloPI;

        sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("EmpId");
        sayHelloPI.setValue(strEmpNum);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("MobileNo");
        sayHelloPI.setValue(strMobileNumber);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        sayHelloPI = new PropertyInfo();
        sayHelloPI.setName("Email");
        sayHelloPI.setValue(strEmail);
        sayHelloPI.setType(String.class);
        request.addProperty(sayHelloPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + "isEmployeeExists", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            Log.e("Check User Geniune:", e.getMessage());
        }
        //Return resTxt to calling object
        return resTxt;
       }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, String> {

//        ProgressDialog dialog;

//        @Override
//        protected void onPreExecute() {
//            dialog = new ProgressDialog(LoginActivity.this);
//            dialog.setTitle("Checking...");
//            dialog.setMessage("Please wait...");
//            dialog.setIndeterminate(true);
//            dialog.show();
//        }

//        @Override
//        protected Boolean onPostExecute() {
//            dialog.hide();
//            return true;
//        }

        @Override
        protected String doInBackground(String... params) {
            return  WebService.isUserGenuine(params[0],params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
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
