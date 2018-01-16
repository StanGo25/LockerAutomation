<<<<<<< HEAD
package ca.goldenphoenicks.lockerauto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText uname;
    EditText upin;

    String validate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button validatePin = (Button) findViewById(R.id.valPin);
        Button validateReg = (Button) findViewById(R.id.valReg);

        uname = (EditText) findViewById(R.id.nameField);
        upin = (EditText) findViewById(R.id.pinField);

        validatePin.setOnClickListener(this);
        validateReg.setOnClickListener(this);

        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1)) {
            case 0:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 3:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            default:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
        }

//        AzureServiceAdapter as = new AzureServiceAdapter(this);
//        as.Initialize(this);
//        try {
//            ArrayList<Users> users = as.getUsers();
//            for(int i = 0; i < users.size(); i++)
//                Toast.makeText(this, users.get(i).getName(),
//                        Toast.LENGTH_SHORT).show();
//        } catch (MobileServiceException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.purp));
//        }


    }

    @Override
    public void onClick(View view) {
        Intent nextAct = new Intent(LoginActivity.this, MenuActivity.class);

        boolean checkPIN;
        switch (view.getId()) {
            case R.id.valPin:

                try {
                    validate=new getPin().execute(uname.getText().toString(),upin.getText().toString()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (!(validate.contains(uname.getText().toString()))) {
                    Context context = getApplicationContext();

                    CharSequence text = getString(R.string.validate_err);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, "Error logging in", duration);
                    toast.show();
                } else {
                    nextAct.putExtra("name",uname.getText().toString());
                    startActivity(nextAct);
                }
                break;

            case R.id.valReg:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Register here");

                LinearLayout layout= new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText inName = new EditText(this);
                inName.setHint(R.string.hint2);

                final EditText inPIN = new EditText(this);
                inPIN.setHint(R.string.hint);
                inPIN.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                layout.addView(inName);
                layout.addView(inPIN);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nextAct = new Intent(LoginActivity.this, MenuActivity.class);

                        String newName;
                        String newPin;
                        String validateReg=null;

                        newName = inName.getText().toString();
                        newPin = inPIN.getText().toString();

                        try {
                            validateReg = new regAccount().execute(newName,newPin).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if(validateReg.contains("Success")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Register Success!", Toast.LENGTH_SHORT);
                            toast.show();
                            nextAct.putExtra("name",newName);
                            startActivity(nextAct);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), validateReg, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                break;

            default:
                break;
        }

    }

    class regAccount extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            String user = strings[0];
            String pin = strings[1];
            String result = "";
            HttpURLConnection urlConnection=null;
            String validateUrl = "http://munro.humber.ca/~n01116269/userinsert.php?user_name=" + user + "&user_pin=" + pin;
            try {
                URL url = new URL(validateUrl);
                urlConnection =(HttpURLConnection)url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line ="";
                while((line=br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    class getPin extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String user = strings[0];
            String pin = strings[1];
            String result = "";
            HttpURLConnection urlConnection=null;
            String validateUrl = "http://munro.humber.ca/~n01116269/db234.php?user_name=" + user + "&user_pin=" + pin;
            try {
                URL url = new URL(validateUrl);
                urlConnection =(HttpURLConnection)url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line ="";
                while((line=br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

=======
package ca.goldenphoenicks.lockerauto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String FILENAME;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FILENAME=getString(R.string.filename);

        Button validatePin=(Button)findViewById(R.id.valPin);
        Button validateReg=(Button)findViewById(R.id.valReg);

        validatePin.setOnClickListener(this);
        validateReg.setOnClickListener(this);

        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1))
        {
            case 0:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 3:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            default:

                getWindow().getDecorView().setBackgroundResource(R.color.blk);
        }

//        AzureServiceAdapter as = new AzureServiceAdapter(this);
//        as.Initialize(this);
//        try {
//            ArrayList<Users> users = as.getUsers();
//            for(int i = 0; i < users.size(); i++)
//                Toast.makeText(this, users.get(i).getName(),
//                        Toast.LENGTH_SHORT).show();
//        } catch (MobileServiceException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.purp));
//        }


    }

    @Override
    public void onClick(View view) {
        Intent nextAct=new Intent(LoginActivity.this,MenuActivity.class);

        boolean checkPIN;
        String pinput=((EditText)findViewById(R.id.pinField)).getText().toString();
        switch(view.getId())
        {
            case R.id.valPin:
                if(!ValidatePin().equals(pinput)) {
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

    public String ValidatePin()
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
>>>>>>> e85493692c1994a6b92a212fedb5823ed428d526
