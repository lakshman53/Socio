package tds.socio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import tds.libs.StringEncrypter;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.btnContinue);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText textuserName = (EditText)findViewById(R.id.textuserName);
                EditText textPassword = (EditText)findViewById(R.id.textPassword);

                String strEmpNum = textuserName.getText().toString();
                String strPasswordDecrypted = textPassword.getText().toString();

                if (authenticateUser(strEmpNum,strPasswordDecrypted)) {
                    //TODO: Validate for username and password
                }
            }
        });
    }

    private boolean authenticateUser(String userName, String Password)
    {
        String PasswordEncrypted = encryptString(Password);
        return true;
    }

    private String encryptString(String strPhrase)
    {
        String desEncrypted = "";

        try {
            SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
            StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());
            desEncrypted = desEncrypter.encrypt(desEncrypted);

            return desEncrypted;
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("encryptString ", e.getMessage());
        }
        return desEncrypted;
    }
}
