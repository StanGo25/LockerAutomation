package ca.goldenphoenicks.lockerauto;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.goldenphoenicks.lockerauto.databases.Users;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String FILENAME;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FILENAME=getString(R.string.filename);

        Button validatePin=(Button)findViewById(R.id.valPin);
        Button validateReg=(Button)findViewById(R.id.valReg);

        validatePin.setOnClickListener(this);
        validateReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent nextAct=new Intent(LoginActivity.this,MenuActivity.class);

        boolean checkPIN;
        String pinput=((EditText)findViewById(R.id.pinField)).getText().toString();
        switch(view.getId())
        {
            case R.id.valPin:
                if(!ValidatePin(pinput).equals(pinput)) {
                    Context context = getApplicationContext();

                    CharSequence text = getString(R.string.validate_err);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    nextAct.putExtra("validate",1);
                    startActivity(nextAct);
                }
                break;

            case R.id.valReg:
                RegisterPIN(pinput);
                nextAct.putExtra("validate",2);
                startActivity(nextAct);
                break;

            default:
                break;
        }

    }


    public String ValidatePin(String PIN)
    {
        String savedPIN = null;
        try {
            InputStream inputStream = openFileInput(FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                savedPIN = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(LoginActivity.class.getName(), "File not found: " + e.toString());

        } catch (IOException e) {
            Log.e(LoginActivity.class.getName(), "Can not read file: " + e.toString());
        }
        return savedPIN;
    }

    public void RegisterPIN(String PIN)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(PIN);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(LoginActivity.class.getName(), "File write failed: " + e.toString());
        }
    }
}
