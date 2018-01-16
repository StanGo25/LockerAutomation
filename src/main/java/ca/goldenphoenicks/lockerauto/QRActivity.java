
package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Stanley on 10/04/17.
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ca.goldenphoenicks.lockerauto.barcode.BarcodeCaptureActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static ca.goldenphoenicks.lockerauto.QRActivity.p_type;

public class QRActivity extends AppCompatActivity {
    private static final String LOG_TAG = QRActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    public static int p_type=0;


    private TextView mResultTextView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blu));



        pref = QRActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        switch (pref.getInt("color", -1))
        {
            case 0:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:
                myToolbar.setBackgroundResource(R.color.purp);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:
                myToolbar.setBackgroundResource(R.color.blu);
                getWindow().getDecorView().setBackgroundResource(R.color.wht);
                break;
            case 3:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.ylw);
                break;
            default:
                myToolbar.setBackgroundResource(R.color.purp);
                getWindow().getDecorView().setBackgroundResource(R.color.wht);
        }

        mResultTextView = (TextView) findViewById(R.id.result_textview);

        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    mResultTextView.setText(barcode.displayValue);

                    int pt;
                    String res;



                    if(((barcode.displayValue).substring(0,2)).equals("JE"))
                    {
                        //JE1234567 = lock
                        mResultTextView.setText("Lock Enabled");

                        try
                        {
                            res = new GetPin().execute(String.valueOf(1),barcode.displayValue).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }




                    }else if(((barcode.displayValue).substring(0,2)).equals("JI"))
                    {
                        //JI1234567 = Door

                        try
                        {
                            res = new GetPin().execute(String.valueOf(2),barcode.displayValue).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }else if(((barcode.displayValue).substring(0,2)).equals("JM"))
                    {
                        //JM1234567 = Display
                        mResultTextView.setText("Display Enabled");

                        try
                        {
                            res = new GetPin().execute(String.valueOf(3),barcode.displayValue).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
//        NavUtils.navigateUpFromSameTask(this);
        Intent in = new Intent(QRActivity.this, MenuActivity.class);}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class GetPin extends AsyncTask<String,Void,String> {
        private Uri uri;

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/dbproducts.php?p_type=" + strings[0] + "&u_name=" + pref.getString("u_name", "")+"&p_name="+strings[1];
            try {
                URL url = new URL(validateUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";

                while((line = br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}

package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Stanley on 10/04/17.
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ca.goldenphoenicks.lockerauto.barcode.BarcodeCaptureActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static ca.goldenphoenicks.lockerauto.QRActivity.p_type;

public class QRActivity extends AppCompatActivity {
    private static final String LOG_TAG = QRActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    public static int p_type=0;


    private TextView mResultTextView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.blu));



        pref = QRActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1))
        {
            case 0:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 3:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            default:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
        }


        mResultTextView = (TextView) findViewById(R.id.result_textview);

        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    mResultTextView.setText(barcode.displayValue);

                    int pt;
                    String res;



                    if(((barcode.displayValue).substring(0,2)).equals("JE"))
                    {
                        //JE1234567 = lock
                        mResultTextView.setText("Lock Enabled");

                        MenuItem nav_item2 = (MenuActivity.menuNav).findItem(R.id.nav_lock);
                        nav_item2.setEnabled(true);
                        try
                        {
                            res = new GetPin().execute(1).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }




                    }else if(((barcode.displayValue).substring(0,2)).equals("JI"))
                    {
                        //JI1234567 = Door

                        mResultTextView.setText("Door Enabled");
                        MenuItem nav_item3 = (MenuActivity.menuNav).findItem(R.id.nav_door);
                        nav_item3.setEnabled(true);
                        try
                        {
                            res = new GetPin().execute(2).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }else if(((barcode.displayValue).substring(0,2)).equals("JM"))
                    {
                        //JM1234567 = Display
                        mResultTextView.setText("Display Enabled");
                        MenuItem nav_item4 = (MenuActivity.menuNav).findItem(R.id.nav_display);
                        nav_item4.setEnabled(true);
                        try
                        {
                            res = new GetPin().execute(3).get();
                            if(res.contains("Success"))
                            {
                                Toast.makeText(QRActivity.this, "Success", LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(QRActivity.this, "Failure", LENGTH_SHORT).show();
                            }

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
//        NavUtils.navigateUpFromSameTask(this);
        Intent in = new Intent(QRActivity.this, MenuActivity.class);}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class GetPin extends AsyncTask<Integer,Void,String> {
        private Uri uri;

        @Override
        protected String doInBackground(Integer... iints) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/dbproducts.php?p_type=" + iints + "&user_name=" + pref.getString("u_name", "");
            try {
                URL url = new URL(validateUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";

                while((line = br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}


