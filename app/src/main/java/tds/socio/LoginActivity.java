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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
  //      if (id == R.id.action_settings) {
            return true;
    //    }

      //  return super.onOptionsItemSelected(item);
    }
*/
}
